package org.crudeemail.controller;

import org.crudeemail.mail.MailAccount;
import org.crudeemail.ResourcesController;

public abstract class AbstractController {

    protected MailAccount mailAccount;
    protected ResourcesController resourcesController;
    private String fxml;

    public AbstractController(MailAccount mailAccount, ResourcesController resourcesController, String fxml) {
        this.mailAccount = mailAccount;
        this.resourcesController = resourcesController;
        this.fxml = fxml;
    }

    public String getFxml() {
        return fxml;
    }
}
