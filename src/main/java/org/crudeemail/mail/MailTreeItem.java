package org.crudeemail.mail;

import jakarta.mail.Flags;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.IOException;

/**
 * <h1>MailTreeItem</h1>
 * Class for handling messages in the folders from treeView to the tableView.
 * Extended from original JavaFX TreeItem.
 * @param <String>
 */
public class MailTreeItem<String> extends TreeItem<String> {

    // Fields
    private String name;
    private ObservableList<MailMessage> mailMessages;
    private int unreadMessages;

    // Constructor
    public MailTreeItem(String name) {
        super(name);
        this.name = name;
        this.mailMessages = FXCollections.observableArrayList();
    }

    // Getters
    public ObservableList<MailMessage> getMailMessages() {
        return mailMessages;
    }

    // Methods
    /**
     * Processes message for display in tableView.
     * Increments the unreadMessage count if the message passed is unread.
     * @param message
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    private MailMessage fetchMail(Message message) throws MessagingException, IOException {
        // Process Message object to get MailMessage object with all of its properties
        boolean isRead = message.getFlags().contains(Flags.Flag.SEEN);
        MailMessage mailMessage = new MailMessage (
                message.getSubject(),
                message.getFrom()[0].toString(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSentDate(),
                isRead,
                message,
                MailProcess.getText(message), // Don't display line breaks,
                MailProcess.hasAttachments(message)
        );

        if (!isRead) {
            incrementUnread();
        }

        return mailMessage;
    }

    public void addMail(Message message) throws MessagingException, IOException {
        // Adds new mailMessage to the bottom of the table
        MailMessage mailMessage = fetchMail(message);

        mailMessages.add(mailMessage);
    }

    public void addMailTop(Message message) throws MessagingException, IOException {
        // Adds new mailMessage to the top of the table
        MailMessage mailMessage = fetchMail(message);

        mailMessages.add(0, mailMessage);
    }

    public void incrementUnread() {
        unreadMessages++;
        updateName();
    }

    public void decrementUnread() {
        unreadMessages--;
        updateName();
    }

    private void updateName() {
    // Sets the value of the MailTreeItem folder name depending on unreadMessages
        if (unreadMessages > 0) {
            this.setValue((String) (name + " (" + unreadMessages + ")"));
        } else {
            this.setValue(name);
        }
    }
}
