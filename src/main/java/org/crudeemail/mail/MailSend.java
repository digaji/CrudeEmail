package org.crudeemail.mail;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.util.List;

public class MailSend extends Service<String> {

    // Fields
    private MailAccount mailAccount;
    private String recipient;
    private String subject;
    private String content;
    private List<File> attachments;

    // Constructor
    public MailSend(MailAccount mailAccount, String recipient, String subject, String content, List<File> attachments) {
        this.mailAccount = mailAccount;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.attachments = attachments;
    }

    // Methods
    @Override
    protected Task<String> createTask() {
        return new Task<>() {
            @Override
            protected String call() {
                try {
                    sendMail();

                    System.out.println("Mail sent!");
                    return "SUCCESS";
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return "NETWORK FAILED";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "UNEXPECTED ERROR";
                }
            }
        };
    }

    private void sendMail() throws MessagingException {
        // Create new message object to send and set some properties
        MimeMessage mimeMessage = new MimeMessage(mailAccount.getSession());
        mimeMessage.setFrom(mailAccount.getAddress());
        mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        mimeMessage.setSubject(subject);

        // Set content to be sent
        Multipart multipart = new MimeMultipart();
        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(content, "text/html; charset=utf-8");
        multipart.addBodyPart(bodyPart);
        mimeMessage.setContent(multipart);

        // Attachment handling
        if (!attachments.isEmpty()) {
            for (File file: attachments) {
                // Create new MimeBodyPart to be added to multipart for each attachment
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                FileDataSource source = new FileDataSource(file.getAbsolutePath());
                mimeBodyPart.setDataHandler(new DataHandler(source));
                mimeBodyPart.setFileName(file.getName());
                multipart.addBodyPart(mimeBodyPart);
            }
        }

        // Send the final message
        Transport transport = mailAccount.getSession().getTransport();
        transport.connect(
                mailAccount.getProp().getProperty("mail.smtp.host"),
                mailAccount.getAddress(),
                mailAccount.getPassword()
        );

        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }
}
