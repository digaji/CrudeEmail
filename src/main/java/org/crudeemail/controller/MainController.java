package org.crudeemail.controller;

import jakarta.mail.MessagingException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.crudeemail.mail.MailAccount;
import org.crudeemail.ResourcesController;
import org.crudeemail.mail.Gmail;

import java.io.IOException;
import java.util.ArrayList;

public class MainController extends AbstractController {

    @FXML
    private WebView htmlContent;

    @FXML
    private TextField mailInput;

    @FXML
    private PasswordField passwordInput;

    public MainController(MailAccount mailAccount, ResourcesController resourcesController, String fxml) {
        super(mailAccount, resourcesController, fxml);
    }

    @FXML
    void changeToTest(MouseEvent event) {
        resourcesController.testWindow();
        Stage currentStage = (Stage) mailInput.getScene().getWindow();
        resourcesController.closeStage(currentStage);
    }

    @FXML
    void loadMail(MouseEvent event) {
        htmlContent.setContextMenuEnabled(false);
        try {
            Gmail client = new Gmail();
            ArrayList<String> contents = client.receive(mailInput.getText(), passwordInput.getText());

            htmlContent.getEngine().loadContent(contents.get(1));

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
