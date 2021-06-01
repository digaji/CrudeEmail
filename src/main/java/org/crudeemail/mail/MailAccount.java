package org.crudeemail.mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public abstract class MailAccount {

    private Properties propSend;
    private Properties propReceive;

    public Properties getPropSend() {
        return propSend;
    }

    public Properties getPropReceive() {
        return propReceive;
    }

    public void setPropSend(Properties prop) {
        this.propSend = prop;
    }

    public void setPropReceive(Properties prop) {
        this.propReceive = prop;
    }

    public void send(String username, String password, String recipients, String cc, String bcc, String subject, String content) throws MessagingException {
        Session sessionObj = Session.getInstance(getPropSend(),
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
        });

        // Create new message object to send
        Message message = new MimeMessage(sessionObj);
        message.setFrom(new InternetAddress(username));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipients)
        );

        if (!cc.equals("")) {
            message.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(cc));
        }

        if (!bcc.equals("")) {
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(bcc));
        }

        // Set message subject and contents
        message.setSubject(subject);
//            message.setContent("<p>HTML Message <b>Example</b></p>", "text/html; charset=utf-8");
        message.setContent(content, "text/plain");

        // Sends the final message
        Transport.send(message);
    }

    public ArrayList<String> receive(String username, String password) throws MessagingException, IOException {
        Session sessionObj = Session.getInstance(getPropReceive(),
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
        });

        // Create Store object to connect to servers
        Store storeObj = sessionObj.getStore("imap");
        storeObj.connect(getPropReceive().getProperty("mail.imap.host"), username, password);

        // Create folder object and open it in read-only mode
        Folder folderObj = storeObj.getFolder("INBOX");
        folderObj.open(Folder.READ_ONLY);

        // Fetch messages from the folder
        Message[] messageObjs = folderObj.getMessages();

        ArrayList<String> processed = new ArrayList<>();
        for (int i = 0, n = 5; i < n; i++) {
            processed.add(ProcessMail.getText(messageObjs[i]));
        }

        // Close all objects
        folderObj.close(false);
        storeObj.close();

        return processed;
    }
}
