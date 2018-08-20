package com.bel.sbmail.controller;

import com.bel.sbmail.config.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Controller
public class AttachmentEmailExampleController {

    @Qualifier("getJavaMailSender")
    @Autowired
    public JavaMailSender emailSender;

    @ResponseBody
    @RequestMapping("/sendAttachmentEmail")
    public String sendAttachmentEmail() throws MessagingException{

        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);

        helper.setTo(MyConstants.FRIEND_EMAIL);
        helper.setSubject("Test email with attachments");

        helper.setText("Hello, testing email with attachments");

        String path1="C:/test/note.txt";
        String path2="C:/test/text2.txt";

        //attachment 1
        FileSystemResource file1 = new FileSystemResource(new File(path1));
        helper.addAttachment("note.txt", file1);

        //attachment 2
        FileSystemResource file2 = new FileSystemResource(new File(path2));
        helper.addAttachment("text2.txt", file2);

        emailSender.send(message);

        return "Email sent!";
    }
}
