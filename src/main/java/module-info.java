module com.example.supplymart {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.MajorProject to javafx.fxml;
    exports com.example.MajorProject;
}