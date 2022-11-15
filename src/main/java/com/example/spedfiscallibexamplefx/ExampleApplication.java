package com.example.spedfiscallibexamplefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExampleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExampleApplication.class.getResource("exemple-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 640);
        stage.setTitle("Exemplo de uso da biblioteca SPEDFiscalLib!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}