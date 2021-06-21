package org.crudeemail;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.crudeemail.controller.*;
import org.crudeemail.mail.MailManage;

import java.io.IOException;

/**
 * <h1>ResourcesController</h1>
 * Class that handles the windows / stages.
 * When a window needs to be called by the program, the resources controller handles it through the variety of methods that it holds.
 * Also handles when a window needs to be closed.
 * @version 1.0
 */
public class ResourcesController {

    // Fields
    private MailManage mailManage;

    // Constructor
    public ResourcesController(MailManage mailManage) {
        this.mailManage = mailManage;
    }

    // Methods
    // Call in window
    public void landingWindow() {
        System.out.println("Landing window called\n");

        AbstractController controller = new LandingController(mailManage, this, "landing.fxml");
        initializeStage(controller, false);
    }

    public void loginGmailWindow() {
        System.out.println("Login Gmail window called\n");

        AbstractController controller = new LoginController(mailManage, this, "loginGmail.fxml");
        initializeStage(controller, false);
    }

    public void loginOutlookWindow() {
        System.out.println("Login Outlook window called\n");

        AbstractController controller = new LoginController(mailManage, this, "loginOutlook.fxml");
        initializeStage(controller, false);
    }

    public void sendWindow() {
        System.out.println("Send window called\n");

        AbstractController controller = new SendController(mailManage, this, "send.fxml");
        initializeStage(controller, false);
    }

    public void mainWindow() {
        System.out.println("Main window called\n");

        AbstractController controller = new MainController(mailManage, this, "main.fxml");
        initializeStage(controller, true);
    }

    /**
     * Handles when a stage needs to be called upon.
     * @param abstractController
     * @param resizable sets if the window can be resized or not
     */
    // Window handling
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
        stage.initStyle(StageStyle.UNIFIED);

        if (!resizable) {
            stage.setResizable(false);
        }

        // Special minimum width and height for main.fxml
        if (abstractController.getFxml().equals("main.fxml")) {
            stage.setMinWidth(1500);
            stage.setMinHeight(720);
        }

        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
