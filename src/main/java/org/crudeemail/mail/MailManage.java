package org.crudeemail.mail;

import jakarta.mail.Flags;
import jakarta.mail.Folder;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>MailManage</h1>
 * Class for managing folders, messages, and new MailAccount objects.
 * @version 1.0
 */
public class MailManage {

    // Fields
    private MailTreeItem<String> folderRoot = new MailTreeItem<>("");
    private List<Folder> foldersList = new ArrayList<>();
    private MailFolderUpdater mailFolderUpdater;
    private MailMessage selectedMessage;
    private MailTreeItem<String> selectedFolder;
    private MailAccount mailAccount;

    // Constructor
    public MailManage() {
        mailFolderUpdater = new MailFolderUpdater(foldersList);

        // Starts multithreading method from MailFolderUpdater (createTask())
        mailFolderUpdater.start();
    }

    // Getters
    public MailTreeItem<String> getFolderRoot() {
        return folderRoot;
    }

    public List<Folder> getFoldersList() {
        return foldersList;
    }

    public MailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public MailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public MailAccount getMailAccount() {
        return mailAccount;
    }

    // Setters
    public void setSelectedMessage(MailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public void setSelectedFolder(MailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    // Methods
    public void addMailAccount(MailAccount mail) {
        mailAccount = mail;
        MailTreeItem<String> treeItem = new MailTreeItem<>(mail.getAddress());
        MailFolderFetch mailFolderFetch = new MailFolderFetch(mail.getStore(), treeItem, foldersList);

        // Starts multithreading method from MailFolderFetch (createTask())
        mailFolderFetch.start();

        folderRoot.getChildren().add(treeItem);
    }

    public void setSelectedRead() {
        // Set selected message in tableView as read
        try {
            selectedMessage.setIsRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementUnread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelectedUnread() {
        // Set selected message in tableView as unread
        try {
            selectedMessage.setIsRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementUnread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelected() {
        // Delete selected message in tableView, message will now only be visible in All Mail
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getMailMessages().remove(selectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
