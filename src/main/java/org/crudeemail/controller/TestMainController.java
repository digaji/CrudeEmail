package org.crudeemail.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import org.crudeemail.mail.MailAccount;
import org.crudeemail.ResourcesController;

public class TestMainController extends AbstractController {

    @FXML
    private TreeView<?> emailTreeView;

    @FXML
    private TableView<?> emailTableView;

    @FXML
    private WebView emailWebView;

    public TestMainController(MailAccount mailAccount, ResourcesController resourcesController, String fxml) {
        super(mailAccount, resourcesController, fxml);
    }

    @FXML
    void preferencesAction(ActionEvent event) {

    }
}
