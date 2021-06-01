package org.crudeemail.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.crudeemail.mail.MailAccount;
import org.crudeemail.ResourcesController;

public class LoginGmailController extends AbstractController {

    @FXML
    private TextField gmailAddressInput;

    @FXML
    private PasswordField gmailPasswordInput;

    @FXML
    private Label gmailErrorLabel;

    public LoginGmailController(MailAccount mailAccount, ResourcesController resourcesController, String fxml) {
        super(mailAccount, resourcesController, fxml);
    }

    @FXML
    void gmailLoginAction(MouseEvent event) {
        resourcesController.testMainWindow();
        Stage currentStage = (Stage) gmailAddressInput.getScene().getWindow();
        resourcesController.closeStage(currentStage);
    }
}
