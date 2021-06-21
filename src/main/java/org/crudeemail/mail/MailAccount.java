package org.crudeemail.mail;

import jakarta.mail.Session;
import jakarta.mail.Store;

import java.util.Properties;

/**
 * <h1>MailAccount</h1>
 * Class that represents an email account. Stores all data related for processing the email account.
 * The provider is determined through the string that is passed through when an object of the class is being instantiated.
 * @version 1.0
 */
public class MailAccount {

    // Fields
    private String address;
    private String password;
    private Properties prop;
    private Store store;
    private Session session;

    // Constructor(s)
    public MailAccount(String provider, String address, String password) {
        prop = new Properties();
        if (provider.equals("gmail")) {
            // Gmail settings
            prop.put("mail.smtp.host", "smtp.gmail.com");

            prop.put("mail.imap.host", "imap.gmail.com");
        } else {
            // Outlook settings
            prop.put("mail.smtp.host", "smtp.office365.com");

            prop.put("mail.imap.host", "outlook.office365.com");
        }

        // General settings
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.imap.port", "993");
        prop.put("mail.imap.ssl.enable", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        this.address = address;
        this.password = password;
    }

    // Getters
    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProp() {
        return prop;
    }

    public Store getStore() {
        return store;
    }

    public Session getSession() {
        return session;
    }

    // Setters
    public void setStore(Store store) {
        this.store = store;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    // Methods
    @Override
    public String toString() {
        return address;
    }
}
