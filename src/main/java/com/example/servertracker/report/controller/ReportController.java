package com.example.servertracker.report.controller;

import com.example.servertracker.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @Autowired
    ReportService reportService;
    @GetMapping("/report")
    public void generateReport(){
        reportService.genReport();

    }

}
