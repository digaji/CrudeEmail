package org.crudeemail.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailManage;

public class LandingController extends AbstractController {

    // JavaFX Components
    @FXML
    private Text crudeEmailLabel;

    // Constructor
    public LandingController(MailManage mailManage, ResourcesController resourcesController, String fxml) {
        super(mailManage, resourcesController, fxml);
    }

    // Methods
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
