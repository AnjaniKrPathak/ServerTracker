package com.example.servertracker.report.controller;

import com.example.servertracker.report.service.ReportService;
import com.example.servertracker.server.data.ServerTableSpace;
import com.example.servertracker.server.service.ServerService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    ServerService serverService;
    @GetMapping("/report")
    public String generateReport(){
        reportService.genReport();
        return "Report Generated";

    }
    @GetMapping("/tablespacereport")
    public ResponseEntity<byte[]> getTableSpaceReport() throws FileNotFoundException {
        try{
        List<ServerTableSpace> tableSpaceList=serverService.getServerTableSpaceDetail();
        //tableSpaceList.add(new ServerTableSpace("NC_DATA",49152,44855.6875,4296.3125,91));
       // tableSpaceList.add(new ServerTableSpace("NC_INDEXES",12288,8090.375,4197.625,65));
        Map<String,Object> tableSpaceParam=new HashMap<>();
        tableSpaceParam.put("CompanyName","Netcracker Technology");
        tableSpaceParam.put("tableSpaceData",new JRBeanCollectionDataSource(tableSpaceList));
        JasperPrint empReport =
                JasperFillManager.fillReport
                        (
                                JasperCompileManager.compileReport(
                                        ResourceUtils.getFile("classpath:tablespace-details.jrxml")
                                                .getAbsolutePath()) // path of the jasper report
                                , tableSpaceParam // dynamic parameters
                                , new JREmptyDataSource()
                        );

        HttpHeaders headers = new HttpHeaders();
        //set the PDF format
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "tablespace_details.pdf");
        //create the report in PDF format
        return new ResponseEntity<byte[]>
                (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }


}
