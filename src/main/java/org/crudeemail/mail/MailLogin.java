package org.crudeemail.mail;

import jakarta.mail.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class MailLogin extends Service<String> {

    // Fields
    private MailAccount mailAccount;
    private MailManage mailManage;

    // Constructor
    public MailLogin(MailAccount mailAccount, MailManage mailManage) {
        this.mailAccount = mailAccount;
        this.mailManage = mailManage;
    }

    // Methods
    private String login() {
        // Authenticate account with Authenticator class object
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailAccount.getAddress(), mailAccount.getPassword());
            }
        };

        try {
            Session session = Session.getInstance(mailAccount.getProp(), authenticator);
            mailAccount.setSession(session);

            // Create Store object to connect to servers
            Store storeObj = session.getStore("imap");
            // Connect to store object
            storeObj.connect(mailAccount.getProp().getProperty("mail.imap.host"),
                    mailAccount.getAddress(),
                    mailAccount.getPassword());

            mailAccount.setStore(storeObj);
            mailManage.addMailAccount(mailAccount);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.out.println("Network Failed");
            return "NETWORK FAILED";
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            System.out.println("Credentials Failed");
            return "CREDENTIALS FAILED";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unexpected Error");
            return "UNEXPECTED ERROR";
        }

        return "SUCCESS";
    }

    @Override
    protected Task<String> createTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                return login();
            }
        };
    }
}
