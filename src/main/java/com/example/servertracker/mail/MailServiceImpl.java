package com.example.servertracker.mail;

import com.example.servertracker.mail.service.MailService;
import com.example.servertracker.mail.data.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.FileSystem;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;
    public void sendEmail(String to,String subject,String body) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);


    }

    @Override
    public String sendSimpleMail(EmailDetails emailDetails) {
        return null;
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details)  {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try{
            mimeMessageHelper =new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());
            FileSystemResource file=new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);
            mailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }
        catch (MessagingException me){
            me.printStackTrace();
            return "Error while Sending Mail";
        }

    }
}
