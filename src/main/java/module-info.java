module org.crudeemail {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;
    requires javafx.web;

    opens org.crudeemail to javafx.fxml;
    exports org.crudeemail;
    exports org.crudeemail.controller;
    opens org.crudeemail.controller to javafx.fxml;
    exports org.crudeemail.provider;
    opens org.crudeemail.provider to javafx.fxml;
}