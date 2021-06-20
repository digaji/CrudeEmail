package org.crudeemail;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.crudeemail.controller.*;
import org.crudeemail.mail.MailManage;

import java.io.IOException;

public class ResourcesController {

    // Fields
    private MailManage mailManage;

    // Constructor
    public ResourcesController(MailManage mailManage) {
        this.mailManage = mailManage;
    }

    // Methods

    // Call in stages
    public void landingWindow() {
        System.out.println("Landing window called");

        AbstractController controller = new LandingController(mailManage, this, "landing.fxml");
        initializeStage(controller, false);
    }

    public void loginGmailWindow() {
        System.out.println("Login Gmail window called");

        AbstractController controller = new LoginGmailController(mailManage, this, "loginGmail.fxml");
        initializeStage(controller, false);
    }

    public void loginOutlookWindow() {
        System.out.println("Login Outlook window called");

        AbstractController controller = new LoginOutlookController(mailManage, this, "loginOutlook.fxml");
        initializeStage(controller, false);
    }

    public void sendWindow() {
        System.out.println("Send window called");

        AbstractController controller = new SendController(mailManage, this, "send.fxml");
        initializeStage(controller, false);
    }

    public void mainWindow() {
        System.out.println("Main window called");

        AbstractController controller = new MainController(mailManage, this, "main.fxml");
        initializeStage(controller, true);
    }

    // Stage handling
    private void initializeStage(AbstractController abstractController, boolean resizable) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(abstractController.getFxml()));
        fxmlLoader.setController(abstractController);

        Parent parent;

        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Crude Email");

        if (!resizable) {
            stage.setResizable(false);
        }

        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
