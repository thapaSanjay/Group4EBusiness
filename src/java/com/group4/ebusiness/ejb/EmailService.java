package com.group4.ebusiness.ejb;

import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * Stateless email service used for registration verification
 * and password recovery messages.
 *
 * Emails are sent to the local FakeSMTP server during development
 * and assignment demonstration.
 */
@Stateless
public class EmailService {

    /**
    * Sends a plain-text email using the local SMTP server.
    *
    * @param recipient destination email address
    * @param subject email subject
    * @param body email message content
    */
    public void sendEmail(String recipient,
                          String subject,
                          String body) {

        try {
            // Configure Jakarta Mail to communicate with FakeSMTP on localhost.
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

            // Send the completed message to the configured SMTP server.
            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to send email.",
                    e
            );
        }
    }
}