package org.crudeemail.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.crudeemail.App;
import java.io.IOException;

public class LandingController {

    @FXML
    void changeToTest(MouseEvent event) throws IOException {
        App.setRoot("test.fxml");
    }
}
