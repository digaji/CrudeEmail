package org.crudeemail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Properties;

public class TestController {

    @FXML
    private TextField mailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField sendtoInput;

    @FXML
    private TextField headerInput;

    @FXML
    private TextArea contentInput;


    @FXML
    private void sendMail(MouseEvent event) {
        System.out.println("Wow");

        // Properties for Gmail TLS email sending
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailInput.getText(), passwordInput.getText());
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailInput.getText()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(sendtoInput.getText())
            );
            message.setSubject(headerInput.getText());
//            message.setContent("<p>HTML Message <b>Example</b></p>", "text/html; charset=utf-8");
            message.setContent(contentInput.getText(), "text/plain");

            Transport.send(message);

            System.out.println("Done");

            mailInput.setText("");
            passwordInput.setText("");
            sendtoInput.setText("");
            headerInput.setText("");
            contentInput.setText("");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed");

            mailInput.setText("");
            passwordInput.setText("");
            sendtoInput.setText("");
            headerInput.setText("");
            contentInput.setText("");
        }
    }
}
