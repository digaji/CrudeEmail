package org.crudeemail.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.crudeemail.mail.MailAccount;
import org.crudeemail.ResourcesController;

public class LandingController extends AbstractController {

    @FXML
    private Text crudeEmailLabel;

    public LandingController(MailAccount mailAccount, ResourcesController resourcesController, String fxml) {
        super(mailAccount, resourcesController, fxml);
    }

    @FXML
    void changeToTest(MouseEvent event) {
        resourcesController.testWindow();
        Stage currentStage = (Stage) crudeEmailLabel.getScene().getWindow();
        resourcesController.closeStage(currentStage);
    }

    @FXML
    void changeToGmail(MouseEvent event) {
        resourcesController.loginGmailWindow();
        Stage currentStage = (Stage) crudeEmailLabel.getScene().getWindow();
        resourcesController.closeStage(currentStage);
    }

    @FXML
    void changeToOutlook(MouseEvent event) {
        resourcesController.loginOutlookWindow();
        Stage currentStage = (Stage) crudeEmailLabel.getScene().getWindow();
        resourcesController.closeStage(currentStage);
    }
}
