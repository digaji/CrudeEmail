package org.crudeemail.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import java.io.IOException;

public final class MailProcess extends Service {

    // Fields
    private MailMessage mailMessage;
    private WebEngine webEngine;
    private StringBuffer stringBuffer;

    // Constructor
    public MailProcess(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();

        this.setOnSucceeded(event -> {
            display();
        });
    }

    public void setMailMessage(MailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    // Methods
    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
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
        webEngine.loadContent(stringBuffer.toString());
    }

    private void load() throws MessagingException, IOException {
        // Clear string buffer and set it to a new message
        stringBuffer.setLength(0);
        Message message = mailMessage.getMessage();

        stringBuffer.append(getText(message));
    }

    public static String getText(Part p) throws MessagingException, IOException {
        // Message processing for display on webView
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // Prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }

    public static String getText(Part p, boolean breaks) throws MessagingException, IOException {
        // Message processing for display on tableView
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();

            // Replace line breaks with a single space
            s = s.replaceAll("\\R", " ");

            return s;
        }

        if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null) {
                    // Replace line breaks with a single space
                    s = s.replaceAll("\\R", " ");

                    return s;
                }
            }
        }

        return null;
    }
}
