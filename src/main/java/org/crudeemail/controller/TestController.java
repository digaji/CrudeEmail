package org.crudeemail.controller;

import jakarta.mail.MessagingException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.crudeemail.App;
import org.crudeemail.provider.Gmail;

import java.io.IOException;

public class TestController {

    @FXML
    private TextField mailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField recipientsInput;

    @FXML
    private TextField subjectInput;

    @FXML
    private TextArea contentInput;


    @FXML
    private void sendMail(MouseEvent event) {
        System.out.println("Wow");

        try {
            Gmail client = new Gmail();
            client.send(mailInput.getText(), passwordInput.getText(), recipientsInput.getText(), "", "", subjectInput.getText(), contentInput.getText());
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
            recipientsInput.setText("");
            contentInput.setText("");
        }
    }

    @FXML
    void changeToLanding(MouseEvent event) throws IOException {
        App.setRoot("landing.fxml");
    }

    @FXML
    void changeToMain(MouseEvent event) throws IOException {
        App.setRoot("main.fxml");
    }
}
