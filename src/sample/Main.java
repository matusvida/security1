package sample;

import encryption.Encrypt;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.awt.Label;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Encrypt/Decrypt tool");
        Button encryptBtn, decryptBtn;
        GridPane grid;
        FileChooser fileChooser;
        Label hash1, hash2, exceptionLabel;
        TextField keyInput;
        HBox hbox;


        grid = new GridPane();
        encryptBtn = new Button("Encrypt file");
        decryptBtn = new Button("Decrypt file");
        keyInput = new TextField();
        keyInput.setPromptText("Type key to encrypt file");
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open file to encrypt");
        hbox = new HBox(10, encryptBtn, decryptBtn);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20, 20, 10, 20));

        grid.add(hbox, 0, 1);
        grid.add(keyInput, 0, 0);

        primaryStage.setScene(new Scene(grid, 300, 275));
        primaryStage.show();

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //SecretKey key;// = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("AES");

        encryptBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String inputFilePath;
                String outputFilePath;
                String stringKey = keyInput.getText();
                byte[] decodedKey = Base64.getDecoder().decode(stringKey);
                SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                File file = fileChooser.showOpenDialog(primaryStage);
                inputFilePath = file.getAbsolutePath();

                try {
                    Encrypt.encrypt(Cipher.ENCRYPT_MODE, file, key);
                    Encrypt.hashComparator(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        decryptBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String inputFilePath;
                String outputFilePath;
                String stringKey = keyInput.getText();
                byte[] decodedKey = Base64.getDecoder().decode(stringKey);
                SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                File file = fileChooser.showOpenDialog(primaryStage);
                inputFilePath = file.getAbsolutePath();

                try {
                    Encrypt.encrypt(Cipher.DECRYPT_MODE, file, key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
