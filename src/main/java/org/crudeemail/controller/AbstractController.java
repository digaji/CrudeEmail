package org.crudeemail.controller;

import org.crudeemail.ResourcesController;
import org.crudeemail.mail.MailManage;

/**
 * <h1>AbstractController</h1>
 * Abstract class that is extended by all controllers (except ResourcesController).
 * Allows the passing of the same MailManage object and ResourcesController object to all the windows and controllers.
 * @version 1.0
 */
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
