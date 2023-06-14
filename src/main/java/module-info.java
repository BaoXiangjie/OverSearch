module com.bxj.oversearch {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.lucene.core;
    requires org.apache.lucene.queryparser;
    requires org.apache.commons.io;

    opens com.bxj.winOverSearch to javafx.fxml;
    exports com.bxj.winOverSearch;
}