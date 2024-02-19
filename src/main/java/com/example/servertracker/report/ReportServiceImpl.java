package com.example.servertracker.report;

import com.example.servertracker.report.data.Item;
import com.example.servertracker.report.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {


    public void genReport() {
        String status = "generated";
        Item item = new Item("test1");
        List<Item> items = new ArrayList<>();
        items.add(new Item("test1"));
        items.add(new Item("test2"));

        byte[] reportContent = getItemReport(items, "pdf");

        // Try block to check for exceptions
        try {

            // Initialize a pointer in file
            // using OutputStream
            OutputStream os = new FileOutputStream("C:/Anjani/Report/file/test.pdf");

            // Starting writing the bytes in it
            os.write(reportContent);

            // Display message onconsole for successful
            // execution
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file connections
            os.close();
        }

        // Catch block to handle the exceptions
        catch (Exception e) {

            // Display exception on console
            System.out.println("Exception: " + e);
        }


    }

    public byte[] getItemReport(List<Item> items, String format) {

        JasperReport jasperReport;

        try {
            jasperReport = (JasperReport)
                    JRLoader.loadObject(ResourceUtils.getFile("item-report.jasper"));
        } catch (FileNotFoundException | JRException e) {
            try {
                File file = ResourceUtils.getFile("classpath:item-report.jrxml");
                jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
                JRSaver.saveObject(jasperReport, "item-report.jasper");
            } catch (FileNotFoundException | JRException ex) {
                throw new RuntimeException(e);
            }
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Item Report");
        JasperPrint jasperPrint;
        byte[] reportContent;

        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            switch (format) {
                case "pdf" -> reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
                case "xml" -> reportContent = JasperExportManager.exportReportToXml(jasperPrint).getBytes();
                default -> throw new RuntimeException("Unknown report format");
            }
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return reportContent;
    }
}
