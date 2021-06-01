package org.crudeemail.controller;

import org.crudeemail.mail.MailAccount;
import org.crudeemail.ResourcesController;

public class LoginOutlookController extends AbstractController {

    public LoginOutlookController(MailAccount mailAccount, ResourcesController resourcesController, String fxml) {
        super(mailAccount, resourcesController, fxml);
    }

}
