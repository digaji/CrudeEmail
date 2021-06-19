package org.crudeemail.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailManage;
import org.crudeemail.mail.MailMessage;
import org.crudeemail.mail.MailProcess;
import org.crudeemail.mail.MailTreeItem;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController extends AbstractController implements Initializable {

    // JavaFX Components
    @FXML
    private TreeView<String> mailTreeView;

    @FXML
    private TableView<MailMessage> mailTableView;

    @FXML
    private TableColumn<MailMessage, String> fromColumn;

    @FXML
    private TableColumn<MailMessage, String> subjectColumn;

    @FXML
    private TableColumn<MailMessage, String> contentColumn;

    @FXML
    private TableColumn<MailMessage, Date> dateColumn;

    @FXML
    private WebView mailWebView;

    private MenuItem markUnread = new MenuItem("Mark as Unread");
    private MenuItem deleteMessage = new MenuItem("Delete Message");

    // Fields
    private MailProcess mailProcess;

    // Constructor
    public MainController(MailManage mailManage, ResourcesController resourcesController, String fxml) {
        super(mailManage, resourcesController, fxml);
    }

    // Methods
    @FXML
    void composeMessageAction() {
        resourcesController.sendWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization methods
        setMailTreeView();
        setMailTableView();
        setFolderSelect();
        setUnreadMessages();
        setMailProcess();
        setMessageSelect();
        setContextMenu();
    }

    private void setMailTreeView() {
        // Set the root of the treeView as the first folder
        mailTreeView.setRoot(mailManage.getFolderRoot());
        mailTreeView.setShowRoot(false);
    }

    private void setMailTableView() {
        // Determine how to populate cells in TableView with cell value factories
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Add right click context menu
        mailTableView.setContextMenu(new ContextMenu(markUnread, deleteMessage));
    }

    private void setFolderSelect() {
        // Determine what happens when a specific mail folder is selected, also ignores mouse clicks to anything other than folders
        mailTreeView.setOnMouseClicked(event -> {
            MailTreeItem<String> current = (MailTreeItem<String>) mailTreeView.getSelectionModel().getSelectedItem();

            if (current != null) {
                mailManage.setSelectedFolder(current);
                mailTableView.setItems(current.getMailMessages());
            }
        });
    }

    private void setUnreadMessages() {
        // Set the rows with unread messages as bolded
        mailTableView.setRowFactory(new Callback<>() {
            @Override
            public TableRow<MailMessage> call(TableView<MailMessage> param) {
                // New class object with overridden method
                return new TableRow<>() {
                    @Override
                    protected void updateItem(MailMessage message, boolean empty) {
                        super.updateItem(message, empty);

                        if (message != null) {
                            if (message.isRead()) {
                                setStyle("");
                            } else {
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }

    private void setMailProcess() {
        // Assign new MailProcess object to mailProcess
        mailProcess = new MailProcess(mailWebView.getEngine());
    }

    private void setMessageSelect() {
        // Determine what happens when a message is selected
        mailTableView.setOnMouseClicked(event -> {
            MailMessage mailMessage = mailTableView.getSelectionModel().getSelectedItem();

            if (mailMessage != null) {
                mailManage.setSelectedMessage(mailMessage);

                if (!mailMessage.isRead()) {
                    mailManage.setSelectedRead();
                }

                mailProcess.setMailMessage(mailMessage);
                mailProcess.restart();
            }
        });
    }

    private void setContextMenu() {
        // Add right click context menu to tableView
        markUnread.setOnAction(event -> {
            mailManage.setSelectedUnread();
        });

        deleteMessage.setOnAction(event -> {
            mailManage.deleteSelected();
            mailWebView.getEngine().loadContent("");
        });
    }
}
