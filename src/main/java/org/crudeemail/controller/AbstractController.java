package org.crudeemail.controller;

import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailManage;

public abstract class AbstractController {

    // Fields
    protected MailManage mailManage;
    protected ResourcesController resourcesController;
    private String fxml;

    // Constructor
    public AbstractController(MailManage mailManage, ResourcesController resourcesController, String fxml) {
        this.mailManage = mailManage;
        this.resourcesController = resourcesController;
        this.fxml = fxml;
    }

    // Methods
    public String getFxml() {
        return fxml;
    }
}
