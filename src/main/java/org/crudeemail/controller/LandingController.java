package org.crudeemail.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailManage;

/**
 * <h1>LandingController</h1>
 * Controller for landing.fxml.
 * Holds a label and 2 buttons that leads to loginGmail.fxml and loginOutlook.fxml.
 * @version 1.0
 */
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
        // Switch to Gmail window
        resourcesController.loginGmailWindow();
        Stage currentStage = (Stage) crudeEmailLabel.getScene().getWindow();
        resourcesController.closeStage(currentStage);
    }

    @FXML
    void changeToOutlook(MouseEvent event) {
        // Switch to Outlook window
        resourcesController.loginOutlookWindow();
        Stage currentStage = (Stage) crudeEmailLabel.getScene().getWindow();
        resourcesController.closeStage(currentStage);
    }
}
