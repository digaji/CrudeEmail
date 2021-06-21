package org.crudeemail;

import javafx.application.Application;
import javafx.stage.Stage;
import org.crudeemail.mail.MailManage;

/**
 * <h1>App</h1>
 * The App class starts the whole program.
 * This class loads in the first window for the user, the landing window.
 * @author Jason J. W.
 * @version 1.0
 */
public class App extends Application {

    /**
     * Calls the landing window while passing in a new MailManage class object
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        ResourcesController resourcesController = new ResourcesController(new MailManage());
        resourcesController.landingWindow();
    }

    /**
     * The main method of the App class, launches the program
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}