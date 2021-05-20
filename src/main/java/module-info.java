module org.crudeemail {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;

    opens org.crudeemail to javafx.fxml;
    exports org.crudeemail;
}