package org.crudeemail.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import org.crudeemail.AttachmentButton;
import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailManage;
import org.crudeemail.mail.MailMessage;
import org.crudeemail.mail.MailProcess;
import org.crudeemail.mail.MailTreeItem;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * <h1>MainController</h1>
 * Controller for main.fxml.
 * Holds TreeView, TableView, WebView, and other components for the main window.
 * Handles how and what messages are displayed depending on the selected folder.
 * @version 1.0
 */
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

    @FXML
    private Label attachmentsLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private Label recipientLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private ProgressBar mainProgressBar;

    @FXML
    private HBox hBoxAttachments;

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
        // Calls a new window for sending mail
        resourcesController.sendWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization methods
        setMailTreeView();
        setMailTableView();
        setFolderSelect();
        setMessagesStyle();
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

    private void setMessagesStyle() {
        // Set the rows with unread messages as bolded and attachments with italic
        mailTableView.setRowFactory(new Callback<>() {
            @Override
            public TableRow<MailMessage> call(TableView<MailMessage> param) {
                // New TableRow object with overridden method
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

                            if (message.isHasAttachments()) {
                                setStyle("-fx-font-style: italic");
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

    /**
     * Sets the current message to the selected message in the tableView.
     * Goes through the process of clearing previous attachments in hBoxAttachments, loading attachments,
     * MailProcess service, and setting the information labels on the right side of the window.
     */
    private void setMessageSelect() {
        // Determine what happens when a message is selected
        mailTableView.setOnMouseClicked(event -> {
            MailMessage mailMessage = mailTableView.getSelectionModel().getSelectedItem();

            if (mailMessage != null) {
                mailManage.setSelectedMessage(mailMessage);

                if (!mailMessage.isRead()) {
                    mailManage.setSelectedRead();
                }

                // Clear previous AttachmentButton objects in the hBox
                hBoxAttachments.getChildren().clear();

                // Sets current message to the one that's selected
                mailProcess.setMailMessage(mailMessage);

                try {
                    loadAttachments(mailMessage);
                } catch (MessagingException ignored) {

                }

                // Starts multithreading method from MailProcess (createTask())
                mailProcess.restart();

                // Set the labels according to the current Message
                senderLabel.setText(mailMessage.getSender());
                recipientLabel.setText(mailMessage.getRecipient());
                subjectLabel.setText(mailMessage.getSubject());
            }
        });
    }

    /**
     * Loads attachments when there are attachments present in the mailMessage.
     * Ignores null files.
     * @param mailMessage mailMessage object to be processed
     * @throws MessagingException
     */
    private void loadAttachments(MailMessage mailMessage) throws MessagingException {
        // Determine what happens when a Message has / doesn't have attachments
        if (mailMessage.isHasAttachments()) {
            attachmentsLabel.setOpacity(1);
            // Iterate through all the attachments
            for (MimeBodyPart mimeBodyPart: mailMessage.getAttachments()) {
                try {
                    // Ignore null files
                    if (!mimeBodyPart.getFileName().equals("null")) {
                        AttachmentButton button = new AttachmentButton(mimeBodyPart, mainProgressBar);
                        hBoxAttachments.getChildren().add(button);
                    }
                } catch (NullPointerException ignored) {
                    /*
                    Sometimes attachments present themselves as null and cause NullPointerException.
                    In these cases, just ignore the null attachment
                    */
                }
            }
        } else {
            // Hide attachmentsLabel if no attachments are present
            attachmentsLabel.setOpacity(0);
        }
    }

    private void setContextMenu() {
        // Add right click context menu to tableView
        markUnread.setOnAction(event -> mailManage.setSelectedUnread());

        deleteMessage.setOnAction(event -> {
            mailManage.deleteSelected();
            mailWebView.getEngine().loadContent("");
        });
    }
}
