package org.crudeemail.mail;

import jakarta.mail.Message;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class MailMessage {

    // Fields

    // Use SimpleStringProperty and SimpleObjectProperty for JavaFX TableView
    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleObjectProperty<Date> date;
    private boolean isRead;
    private Message message;
    private String content;

    // Constructor
    public MailMessage(String subject, String sender, String recipient, Date date, boolean isRead, Message message, String content) {
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.date = new SimpleObjectProperty<>(date);
        this.isRead = isRead;
        this.message = message;
        this.content = content;
    }

    // Getters
    public String getSubject() {
        return this.subject.get();
    }

    public String getSender() {
        return this.sender.get();
    }

    public String getRecipient() {
        return this.recipient.get();
    }

    public Date getDate() {
        return this.date.get();
    }

    public boolean isRead() {
        return this.isRead;
    }

    public Message getMessage() {
        return this.message;
    }

    public String getContent() {
        return this.content;
    }

    // Setters
    public void setIsRead(boolean read) {
        this.isRead = read;
    }

}
