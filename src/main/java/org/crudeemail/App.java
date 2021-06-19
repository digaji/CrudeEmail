package org.crudeemail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.crudeemail.mail.MailManage;

import java.io.IOException;

/**
 * Main JavaFX Client
 * <p></p>
 * @version 1.0
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        ResourcesController resourcesController = new ResourcesController(new MailManage());
        resourcesController.landingWindow();
    }

    @Override
    public void stop() {

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}