package org.crudeemail.controller;

import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailManage;

public class LoginOutlookController extends AbstractController {

    public LoginOutlookController(MailManage mailManage, ResourcesController resourcesController, String fxml) {
        super(mailManage, resourcesController, fxml);
    }

}
