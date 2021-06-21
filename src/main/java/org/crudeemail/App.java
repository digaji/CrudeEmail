package org.crudeemail;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.crudeemail.mail.MailManage;

/**
 * Main JavaFX Client
 * <p></p>
 * @version 1.0
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ResourcesController resourcesController = new ResourcesController(new MailManage());
        resourcesController.landingWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}