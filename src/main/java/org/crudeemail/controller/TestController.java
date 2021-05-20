package org.crudeemail.controller;

import jakarta.mail.MessagingException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.crudeemail.SendMail;

public class TestController {

    @FXML
    private TextField mailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField recipientsInput;

    @FXML
    private TextField headerInput;

    @FXML
    private TextArea contentInput;


    @FXML
    private void sendMail(MouseEvent event) {
        System.out.println("Wow");

        try {
            SendMail.send(mailInput.getText(), passwordInput.getText(), recipientsInput.getText(), headerInput.getText(), contentInput.getText());
            System.out.println("Success");
        } catch (MessagingException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Credential Error");
            alert.setHeaderText("Error in inputted credentials!");
            alert.show();

            System.out.println("Failed");
//            e.printStackTrace();
        } finally {
            System.out.println("Done");
            mailInput.setText("");
            passwordInput.setText("");
            recipientsInput.setText("");
            headerInput.setText("");
            contentInput.setText("");
        }
    }
}
