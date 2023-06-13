module com.bxj.oversearch {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.bxj.overSearch to javafx.fxml;
    exports com.bxj.overSearch;
}