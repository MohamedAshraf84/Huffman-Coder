package com.mohamed13ashraf.huffmancoder;

import com.mohamed13ashraf.huffmancoder.HuffmanCoding.HuffmanCoderController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class HuffmanCoder extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HuffmanCoder.class.getResource("MainWindowUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 380);

        stage.setTitle("Huffman Coder");
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setResizable(false);

        InputStream inputStream = getClass().getResourceAsStream("/icons/app_icon.png");
        if (inputStream != null)
        {
            Image icon = new Image(inputStream);
            stage.getIcons().add(icon);
        }
        HuffmanCoderController huffmanCoderController = fxmlLoader.getController();
        huffmanCoderController.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}