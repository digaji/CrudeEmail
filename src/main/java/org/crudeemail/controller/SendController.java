package org.crudeemail.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailManage;
import org.crudeemail.mail.MailSend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    @FXML
    private Button sendMailButton;

    @FXML
    private Button attachmentsButton;

    @FXML
    private ProgressBar sendProgressBar;

    // Fields
    private List<File> attachments = new ArrayList<>();

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
                contentInput.getHtmlText(),
                attachments
        );

        // Starts multithreading method from MailSend (createTask())
        mailSend.start();
        // Disable send button and make progress bar visible
        sendProgressBar.setOpacity(1);
        sendMailButton.setDisable(true);

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
            // Re-enable send button and make progress bar invisible
            sendMailButton.setDisable(false);
            sendProgressBar.setOpacity(0);
        });
    }

    @FXML
    void attachmentsButtonAction(ActionEvent event) {
        // Determine what happens when attachment button is pressed
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        // Adds file from FileChooser and increment the attachment button label
        if (file != null) {
            attachments.add(file);
            attachmentsButton.setText("Attachments: " + attachments.size());
        }
    }
}
