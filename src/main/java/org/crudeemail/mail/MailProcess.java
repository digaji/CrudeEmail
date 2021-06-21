package org.crudeemail.mail;

import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import java.io.IOException;

/**
 * <h1>MailProcess</h1>
 * Class that handles all MailMessage content processing. Also handles the service to load and display to the main window webView.
 * Service is called in MainController.
 * 2 static methods are used in MailTreeItem.
 * @version 1.0
 */
public final class MailProcess extends Service<Void> {

    // Fields
    private MailMessage mailMessage;
    private WebEngine webEngine;
    private StringBuffer stringBuffer;

    // Constructor
    public MailProcess(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();

        this.setOnSucceeded(event -> display());
    }

    public void setMailMessage(MailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    // Methods
    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                try {
                    load();
                } catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private void display() {
        // Display contents of string buffer to webView
        webEngine.loadContent(stringBuffer.toString());
    }

    private void load() throws MessagingException, IOException {
        // Clear string buffer and set it to a new message
        stringBuffer.setLength(0);
        Message message = mailMessage.getMessage();
        String contentType = message.getContentType();

        if (isSimple(contentType)) {
            stringBuffer.append(message.getContent().toString());
        } else if (isMultipart(contentType)) {
            Multipart multipart = (Multipart) message.getContent();
            loadMulti(multipart, stringBuffer);
        }
    }

    private boolean isSimple(String contentType) {
        return contentType.contains("TEXT/HTML") || contentType.contains("mixed") || contentType.contains("text");
    }

    private boolean isMultipart(String contentType) {
        return contentType.contains("multipart");
    }

    private boolean isPlain(String contentType) {
        return contentType.contains("TEXT/PLAIN");
    }

    private void loadMulti(Multipart multipart, StringBuffer stringBuffer) throws MessagingException, IOException {
        // Processing for multipart messages
        for (int i = multipart.getCount() - 1; i >= 0; i--) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            String contentType = bodyPart.getContentType();

            if (isSimple(contentType)) {
                stringBuffer.append(bodyPart.getContent().toString());
            } else if (isMultipart(contentType)) {
                Multipart multipart2 = (Multipart) bodyPart.getContent();
                loadMulti(multipart2, stringBuffer);
            } else if (!isPlain(contentType)) {
                MimeBodyPart mimeBodyPart = (MimeBodyPart) bodyPart;
                mailMessage.addAttachment(mimeBodyPart);
            }
        }
    }

    /**
     * Static method used when a MailMessage is being instantiated in MailTreeItem.
     * Returns a string version of the message content without line breaks.
     * @param p
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    public static String getText(Part p) throws MessagingException, IOException {
        // Message processing for display on tableView
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();

            // Replace line breaks with a single space
            s = s.replaceAll("\\R", " ");

            return s;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null) {
                    return s;
                }
            }
        }
        return null;
    }
    // getText method is from https://javaee.github.io/javamail/FAQ, modified to exclude line breaks

    /**
     * Static method used when a MailMessage is being instantiated in MailTreeItem.
     * Returns true if the message contains attachments. Otherwise returns false.
     * @param message
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    public static boolean hasAttachments(Message message) throws MessagingException, IOException {
        // Determines if the message has attachments by seeing the number of BodyPart objects in the message
        if (message.isMimeType("multipart/mixed")) {
            Multipart multipart = (Multipart) message.getContent();
            return multipart.getCount() > 1;
        }
        return false;
    }
}
