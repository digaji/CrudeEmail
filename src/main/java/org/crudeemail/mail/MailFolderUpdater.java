package org.crudeemail.mail;

import jakarta.mail.Folder;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

/**
 * <h1>MailFolderUpdater</h1>
 * Class for checking and updating new messages in open folders every 5 seconds.
 * Service is called when MailManage is instantiated.
 * @version 1.0
 */
public class MailFolderUpdater extends Service<Void> {

    // Fields
    private List<Folder> folders;

    // Constructor
    public MailFolderUpdater(List<Folder> folderList) {
        this.folders = folderList;
    }

    // Methods
    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                // Infinite loop
                // Checks the message count of open folders to determine if there are new messages
                while (true) {
                    try {
                        Thread.sleep(5000); // Every 5 seconds
                        for (Folder folder: folders) {
                            if (folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()) {
                                folder.getMessageCount();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
