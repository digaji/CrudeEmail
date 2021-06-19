package org.crudeemail.mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class MailSend extends Service<String> {

    // Fields
    private MailAccount mailAccount;
    private String recipient;
    private String subject;
    private String content;

    // Constructor
    public MailSend(MailAccount mailAccount, String recipient, String subject, String content) {
        this.mailAccount = mailAccount;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }

    // Methods
    @Override
    protected Task<String> createTask() {
        return new Task() {
            @Override
            protected String call() throws Exception {
                try {
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

                    // Send the final message
                    Transport transport = mailAccount.getSession().getTransport();
                    transport.connect(
                            mailAccount.getProp().getProperty("mail.smtp.host"),
                            mailAccount.getAddress(),
                            mailAccount.getPassword()
                    );
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                    transport.close();

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
}
