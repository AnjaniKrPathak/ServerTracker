package com.example.servertracker.mail.service;

import com.example.servertracker.mail.data.EmailDetails;
import jakarta.mail.MessagingException;

public interface MailService  {
    String sendSimpleMail(EmailDetails emailDetails);
    String sendMailWithAttachment(EmailDetails emailDetails) throws MessagingException;
}
