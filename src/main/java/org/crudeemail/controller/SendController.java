package org.crudeemail.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailManage;
import org.crudeemail.mail.MailSend;

public class SendController extends AbstractController {

    // JavaFX Components
    @FXML
    private TextField recipientInput;

    @FXML
    private TextField subjectInput;

    @FXML
    private HTMLEditor contentInput;

    @FXML
    private Label sendErrorLabel;

    // Constructor
    public SendController(MailManage mailManage, ResourcesController resourcesController, String fxml) {
        super(mailManage, resourcesController, fxml);
    }

    // Methods
    @FXML
    void sendMail() {
        MailSend mailSend = new MailSend(
                mailManage.getMailAccount(),
                recipientInput.getText(),
                subjectInput.getText(),
                contentInput.getHtmlText()
        );
        mailSend.start();

        mailSend.setOnSucceeded(event -> {

            String result = mailSend.getValue();

            switch (result) {
                case "SUCCESS":
                    Stage currentStage = (Stage) recipientInput.getScene().getWindow();
                    resourcesController.closeStage(currentStage);
                    break;
                case "UNEXPECTED ERROR":
                    sendErrorLabel.setText("Unexpected Error");
                    break;
                case "NETWORK FAILED":
                    sendErrorLabel.setText("Network Error");
                    break;
            }
        });
    }

}
