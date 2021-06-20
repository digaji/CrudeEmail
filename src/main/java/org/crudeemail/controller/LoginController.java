package org.crudeemail.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailAccount;
import org.crudeemail.mail.MailLogin;
import org.crudeemail.mail.MailManage;

public class LoginController extends AbstractController {

    // JavaFX Components
    @FXML
    private TextField addressInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private ProgressBar progressBar;

    // Constructor
    public LoginController(MailManage mailManage, ResourcesController resourcesController, String fxml) {
        super(mailManage, resourcesController, fxml);
    }

    // Methods
    @FXML
    void loginAction(MouseEvent mouseEvent) {
        if (validFields()) {
            String provider;
            // Set provider depending on window being opened

            if (getFxml().equals("loginGmail.fxml")) {
                provider = "gmail";
            } else {
                provider = "outlook";
            }

            MailAccount mailAccount = new MailAccount(provider, addressInput.getText(), passwordInput.getText());
            MailLogin login = new MailLogin(mailAccount, mailManage);

            loginButton.setDisable(true);

            // Start and execute login JavaFX service for multithreading
            login.start();
            login.setOnSucceeded(event -> {

                String result = login.getValue();

                switch (result) {
                    case "SUCCESS":
                        System.out.println("Logged in to: " + mailAccount + "\n");

                        // Switch to main window
                        resourcesController.mainWindow();
                        Stage currentStage = (Stage) addressInput.getScene().getWindow();
                        resourcesController.closeStage(currentStage);
                        break;
                    case "CREDENTIALS FAILED":
                        errorLabel.setText("INVALID CREDENTIALS");
                        break;
                    case "UNEXPECTED ERROR":
                        errorLabel.setText("UNEXPECTED ERROR");
                        break;
                    case "NETWORK FAILED":
                        errorLabel.setText("NETWORK FAILED");
                        break;
                }

                // Reset fields
                addressInput.setText("");
                passwordInput.setText("");
                loginButton.setDisable(false);
                progressBar.setOpacity(0);
            });
        }
    }

    private boolean validFields() {
        // Checks if the input fields have been filled in or not
        if (addressInput.getText().isEmpty()) {
            errorLabel.setText("Please fill in email!");
            return false;
        } else if (passwordInput.getText().isEmpty()) {
            errorLabel.setText("Please fill in password!");
            return false;
        }

        progressBar.setOpacity(1);
        return true;
    }

    @FXML
    void changeToLanding(MouseEvent event) {
        resourcesController.landingWindow();
        Stage currentStage = (Stage) addressInput.getScene().getWindow();
        resourcesController.closeStage(currentStage);
    }
}
