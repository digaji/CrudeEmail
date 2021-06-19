package org.crudeemail.mail;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;
import jakarta.mail.event.MessageCountEvent;
import jakarta.mail.event.MessageCountListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.List;

public class MailFolderFetch extends Service<Void> {

    // Fields
    private Store store;
    private MailTreeItem<String> folderRoot;
    private List<Folder> foldersList;

    // Constructor
    public MailFolderFetch(Store store, MailTreeItem<String> folderRoot, List<Folder> foldersList) {
        this.store = store;
        this.folderRoot = folderRoot;
        this.foldersList = foldersList;
    }

    // Methods
    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                fetchFolders();
                return null;
            }
        };
    }

    private void fetchFolders() throws MessagingException {
        // Store folders in a Folder array
        Folder[] folders = store.getDefaultFolder().list();
        handleFolders(folders, folderRoot);
    }

    private void handleFolders(Folder[] folders, MailTreeItem<String> folderRoot) throws MessagingException {
        // Iterate through folders and add them to folderRoot
        for (Folder folder : folders) {
            foldersList.add(folder);
            MailTreeItem<String> mailTreeItem = new MailTreeItem<>(folder.getName());
            folderRoot.getChildren().add(mailTreeItem);
            folderRoot.setExpanded(true);

            // Get messages from folder
            fetchMessagesFromFolder(folder, mailTreeItem);

            // Continuously look for new messages
            addMessageListener(folder, mailTreeItem);

            // Get subfolders from primary folder
            if (folder.getType() == Folder.HOLDS_FOLDERS) {
                handleFolders(folder.list(), mailTreeItem);
            }
        }

    }

    private void fetchMessagesFromFolder(Folder folder, MailTreeItem<String> mailTreeItem) {
        Service fetchMessages = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        // Check if folder has anymore additional subfolders
                        if (folder.getType() != Folder.HOLDS_FOLDERS) {
                            // Get the amount of messages and the messages themselves
                            folder.open(Folder.READ_WRITE);
                            int size = folder.getMessageCount();

                            // Get messages from newest to oldest
                            for (int i = size; i > 0; i--) {
                                mailTreeItem.addMail(folder.getMessage(i));
                            }
                        }
                        return null;
                    }
                };
            }
        };
        fetchMessages.start();
    }

    private void addMessageListener(Folder folder, MailTreeItem<String> mailTreeItem) {
        // Continuously looks for updated messages in specified folder
        folder.addMessageCountListener(new MessageCountListener() {
            @Override
            public void messagesAdded(MessageCountEvent e) {
                for (int i = 0; i < e.getMessages().length; i++) {
                    try {
                        Message current = folder.getMessage(folder.getMessageCount() - i);
                        mailTreeItem.addMailTop(current);
                    } catch (MessagingException | IOException messagingException) {
                        messagingException.printStackTrace();
                    }
                }
            }
            @Override
            public void messagesRemoved(MessageCountEvent e) {
                System.out.println("Message removed " + e);
            }
        });
    }
}