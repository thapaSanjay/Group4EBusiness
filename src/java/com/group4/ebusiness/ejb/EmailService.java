package com.group4.ebusiness.ejb;

import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

@Stateless
public class EmailService {

    public void sendEmail(String recipient,
                          String subject,
                          String body) {

        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.host", "localhost");
            properties.put("mail.smtp.port", "2525");
            properties.put("mail.smtp.auth", "false");
            properties.put("mail.smtp.starttls.enable", "false");

            Session session = Session.getInstance(properties);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(
                    new InternetAddress("noreply@group4ebusiness.com")
            );

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipient)
            );

            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to send email.",
                    e
            );
        }
    }
}