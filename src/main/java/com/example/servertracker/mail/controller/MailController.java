package com.example.servertracker.mail.controller;

import com.example.servertracker.mail.data.EmailDetails;
import com.example.servertracker.mail.service.MailService;
import com.example.servertracker.report.ReportServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
    @Autowired
    MailService mailService;
    @Autowired
    ReportServiceImpl c;
    @GetMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailDetails details){
        String status ="Hello";

        return status;
    }

    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) throws MessagingException {
       System.out.println("Hello");
       c.genReport();
        String status =mailService.sendMailWithAttachment(details);
        return status;

    }
}
