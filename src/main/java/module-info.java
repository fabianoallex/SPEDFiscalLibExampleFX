module com.example.spedfiscallibexamplefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires SPEDFiscalLib;

    opens com.example.spedfiscallibexamplefx to javafx.fxml;
    exports com.example.spedfiscallibexamplefx;
}