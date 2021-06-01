package org.crudeemail;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.crudeemail.mail.Gmail;

/**
 * Main JavaFX Client
 * <p></p>
 * @version 1.0
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        ResourcesController resourcesController = new ResourcesController(new Gmail());
        resourcesController.landingWindow();
    }

    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}