package org.crudeemail.mail;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeBodyPart;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>MailMessage</h1>
 * Class that represents an email message. Stores all data related to the email message.
 * Also handles the checks for new attachments in an email message.
 * @version 1.0
 */
public class MailMessage {

    // Fields

    // Use SimpleStringProperty and SimpleObjectProperty for JavaFX TableView
    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleObjectProperty<Date> date;
    private List<MimeBodyPart> attachments = new ArrayList<>();
    private boolean hasAttachments;
    private boolean isRead;
    private Message message;
    private String content;

    // Constructor
    public MailMessage(String subject, String sender, String recipient, Date date, boolean isRead, Message message, String content, boolean hasAttachments) {
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.date = new SimpleObjectProperty<>(date);
        this.isRead = isRead;
        this.message = message;
        this.content = content;
        this.hasAttachments = hasAttachments;
    }

    // Getters
    public String getSubject() {
        return subject.get();
    }

    public String getSender() {
        return sender.get();
    }

    public String getRecipient() {
        return recipient.get();
    }

    public Date getDate() {
        return date.get();
    }

    public List<MimeBodyPart> getAttachments() {
        return attachments;
    }

    public boolean isHasAttachments() {
        return hasAttachments;
    }

    public boolean isRead() {
        return isRead;
    }

    public Message getMessage() {
        return message;
    }

    public String getContent() {
        return content;
    }

    // Setters
    public void setIsRead(boolean read) {
        this.isRead = read;
    }

    // Methods
    public void addAttachment(MimeBodyPart mimeBodyPart) {
        // Only add new attachments
        if (!attachments.contains(mimeBodyPart)) {
            attachments.add(mimeBodyPart);
        }
    }
}
