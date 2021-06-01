package org.crudeemail.mail;

import java.util.Properties;

public class Outlook extends MailAccount {

    public Outlook() {
        Properties propSend = new Properties();
        propSend.put("mail.smtp.host", "smtp.office365.com");
        propSend.put("mail.smtp.port", "587");
        propSend.put("mail.smtp.auth", "true");
        propSend.put("mail.smtp.starttls.enable", "true"); // Enable TLS
        setPropSend(propSend);

        Properties propReceive = new Properties();
        propReceive.put("mail.imap.host", "outlook.office365.com");
        propReceive.put("mail.imap.port", "993");
        propReceive.put("mail.imap.ssl.enable", "true");
        setPropReceive(propReceive);
    }
}
