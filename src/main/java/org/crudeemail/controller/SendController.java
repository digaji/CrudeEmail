package org.crudeemail.controller;

import org.crudeemail.mail.MailAccount;
import org.crudeemail.ResourcesController;

public class SendController extends AbstractController {

    public SendController(MailAccount mailAccount, ResourcesController resourcesController, String fxml) {
        super(mailAccount, resourcesController, fxml);
    }

}
