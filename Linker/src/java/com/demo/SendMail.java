/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Tapos
 */
public class SendMail {
    
    public static void sendMail(String fromEmail,String username,String password,String toEmail,String subject,String message){
           System.out.println("etoruk paici jak baca gelo");
        try {
            Properties props =System.getProperties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.fallback", "false");
            
            Session mailSesseion =Session.getDefaultInstance(props,null);
            mailSesseion.setDebug(true);
            
            Message mailMessage =new MimeMessage(mailSesseion);
            mailMessage.setFrom(new InternetAddress(fromEmail));
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mailMessage.setContent(message,"text/html; charset=utf-8");
            mailMessage.setSubject(subject);
            
            Transport transport =mailSesseion.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
            System.out.println("Successfully send email");
            
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        
    }
    
}
