package org.crudeemail.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailAccount;
import org.crudeemail.mail.MailLogin;
import org.crudeemail.mail.MailManage;

public class LoginGmailController extends AbstractController {

    // JavaFX Components
    @FXML
    private TextField gmailAddressInput;

    @FXML
    private PasswordField gmailPasswordInput;

    @FXML
    private Label gmailErrorLabel;

    @FXML
    private Button gmailLoginButton;

    @FXML
    private ProgressBar gmailProgressBar;

    // Constructor
    public LoginGmailController(MailManage mailManage, ResourcesController resourcesController, String fxml) {
        super(mailManage, resourcesController, fxml);
    }

    // Methods
    @FXML
    void gmailLoginAction(MouseEvent mouseEvent) {
        if (validFields()) {
            MailAccount mailAccount = new MailAccount("gmail", gmailAddressInput.getText(), gmailPasswordInput.getText());
            MailLogin login = new MailLogin(mailAccount, mailManage);

            gmailLoginButton.setDisable(true);

            // Start and execute login JavaFX service for multithreading
            login.start();
            login.setOnSucceeded(event -> {

                String result = login.getValue();

                switch (result) {
                    case "SUCCESS":
                        System.out.println("Logged in to: " + mailAccount);

                        // Switch to main window
                        resourcesController.mainWindow();
                        Stage currentStage = (Stage) gmailAddressInput.getScene().getWindow();
                        resourcesController.closeStage(currentStage);
                        break;
                    case "CREDENTIALS FAILED":
                        gmailErrorLabel.setText("INVALID CREDENTIALS");
                        break;
                    case "UNEXPECTED ERROR":
                        gmailErrorLabel.setText("UNEXPECTED ERROR");
                        break;
                    case "NETWORK FAILED":
                        gmailErrorLabel.setText("NETWORK FAILED");
                        break;
                }

                // Reset fields
                gmailAddressInput.setText("");
                gmailPasswordInput.setText("");
                gmailLoginButton.setDisable(false);
                gmailProgressBar.setOpacity(0);
            });
        }
    }

    private boolean validFields() {
        // Checks if the input fields have been filled in or not
        if (gmailAddressInput.getText().isEmpty()) {
            gmailErrorLabel.setText("Please fill in email!");
            return false;
        } else if (gmailPasswordInput.getText().isEmpty()) {
            gmailErrorLabel.setText("Please fill in password!");
            return false;
        }

        gmailProgressBar.setOpacity(1);
        return true;
    }
}
