package org.crudeemail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.awt.*;
import java.io.File;

/**
 * <h1>AttachmentButton</h1>
 * Class to represent the attachment buttons in the right side of the main window.
 * Only gets called in when attachment(s) are present in the email.
 * Extended from original JavaFX Button.
 * @version 1.0
 */
public class AttachmentButton extends Button {

    // Fields
    private MimeBodyPart mimeBodyPart;
    private ProgressBar progressBar;
    private final String DOWNLOADS = System.getProperty("user.home") + "/Downloads/";
    private final String downloadPath;

    // Constructor
    /**
     * Constructor for AttachmentButton. Starts downloadAttachment method when clicked.
     * @param mimeBodyPart the MimeBodyPart object which holds the attachment
     * @param progressBar
     * @throws MessagingException
     */
    public AttachmentButton(MimeBodyPart mimeBodyPart, ProgressBar progressBar) throws MessagingException {
        this.mimeBodyPart = mimeBodyPart;
        this.progressBar = progressBar;
        this.setText(mimeBodyPart.getFileName());
        this.downloadPath = DOWNLOADS + mimeBodyPart.getFileName();

        this.setOnAction(event -> downloadAttachment());
    }

    // Methods
    /**
     * Service for handling attachment downloads.
     * Sets progress bar to visible and button to disabled while the service is running.
     * Prints "Downloaded to: " and the path of the file.
     * When the service is finished, the button is set with a green background and further clicks will open the attachment externally.
     */
    private void downloadAttachment() {
        // Saves the attachment to the download path
        Service<Void> service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        // Set progress bar to visible and button to disabled
                        progressBar.setOpacity(1);
                        disabled(true);

                        mimeBodyPart.saveFile(downloadPath);
                        System.out.println("Downloaded to: " + downloadPath + "\n");
                        return null;
                    }
                };
            }
        };
        service.restart();

        service.setOnSucceeded(event -> {
            // Set progress bar to invisible and button to enabled with green background
            disabled(false);
            setGreen();
            progressBar.setOpacity(0);

            // Opens the attachment if the user clicks after download has finished
            this.setOnAction(event2 -> {
                File file = new File(downloadPath);
                Desktop desktop = Desktop.getDesktop();

                if (file.exists()) {
                    try {
                        desktop.open(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    private void setGreen() {
        this.setStyle("-fx-background-color: Green");
    }

    private void disabled(boolean disabled) {
        this.setDisabled(disabled);
    }
}
