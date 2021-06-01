package org.crudeemail;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.crudeemail.controller.*;
import org.crudeemail.mail.MailAccount;

import java.io.IOException;

public class ResourcesController {

    private MailAccount mailAccount;

    public ResourcesController(MailAccount mailAccount) {
        this.mailAccount = mailAccount;
    }

    // Call in stages
    public void landingWindow() {
        System.out.println("Landing window called");

        AbstractController controller = new LandingController(mailAccount, this, "landing.fxml");
        initializeStage(controller);
    }

    public void loginGmailWindow() {
        System.out.println("Login Gmail window called");

        AbstractController controller = new LoginGmailController(mailAccount, this, "loginGmail.fxml");
        initializeStage(controller);
    }

    public void loginOutlookWindow() {
        System.out.println("Login Outlook window called");

        AbstractController controller = new LoginOutlookController(mailAccount, this, "loginOutlook.fxml");
        initializeStage(controller);
    }

    public void mainWindow() {
        System.out.println("Main window called");

        AbstractController controller = new MainController(mailAccount, this, "main.fxml");
        initializeStage(controller);
    }

    public void sendWindow() {
        System.out.println("Send window called");

        AbstractController controller = new SendController(mailAccount, this, "send.fxml");
        initializeStage(controller);
    }

    public void testWindow() {
        System.out.println("Test window called");

        AbstractController controller = new TestController(mailAccount, this, "test.fxml");
        initializeStage(controller);
    }

    public void testMainWindow() {
        System.out.println("Test main window called");

        AbstractController controller = new TestMainController(mailAccount, this, "testMain.fxml");
        initializeStage(controller);
    }

    // Stage handling
    private void initializeStage(AbstractController abstractController) {
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
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
