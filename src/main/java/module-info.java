module org.crudeemail {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.crudeemail to javafx.fxml;
    exports org.crudeemail;
}