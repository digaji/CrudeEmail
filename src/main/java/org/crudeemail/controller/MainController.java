package org.crudeemail.controller;

import jakarta.mail.MessagingException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import org.crudeemail.App;
import org.crudeemail.provider.Gmail;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    @FXML
    private WebView htmlContent;

    @FXML
    private TextField mailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    void changeToTest(MouseEvent event) throws IOException {
        App.setRoot("test.fxml");
    }

    @FXML
    void loadMail(MouseEvent event) {
        htmlContent.setContextMenuEnabled(false);
        try {
            Gmail client = new Gmail();
            ArrayList<String> contents = client.receive(mailInput.getText(), passwordInput.getText());

            htmlContent.getEngine().loadContent(contents.get(3));

            System.out.println("Success");
        } catch (MessagingException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Credential Error");
            alert.setHeaderText("Error in inputted credentials!");
            alert.show();

            System.out.println("Failed");
            e.printStackTrace();
        } finally {
            System.out.println("Done");
            mailInput.setText("");
            passwordInput.setText("");
        }
    }

}
