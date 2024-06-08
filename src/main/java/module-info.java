module com.example.lms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.xmlbeans;
    requires org.apache.commons.collections4;
    requires org.apache.commons.compress;

    opens com.example.lms to javafx.fxml;
    opens com.example.lms.Datas to javafx.base;
    opens com.example.lms.DataBase to javafx.base;

    exports com.example.lms;
}
