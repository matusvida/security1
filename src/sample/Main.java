package sample;

import encryption.Encrypt;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javafx.scene.text.Font;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private Desktop desktop = Desktop.getDesktop();


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Encrypt/Decrypt tool");
        Button encryptBtn, decryptBtn, chooseFileBtn;
        GridPane grid;
        FileChooser fileChooser;
        Label hash1, hash2, exceptionLabel, timeLabel, pathLabel;
        TextField keyInput;
        HBox hbox;
        final File[] file = new File[1];

        grid = new GridPane();
        encryptBtn = new Button("Encrypt file");
        decryptBtn = new Button("Decrypt file");
        chooseFileBtn = new Button("Choose File");
        keyInput = new TextField();
        keyInput.setPromptText("Type key to encrypt/decrypt file");
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open file to encrypt");
        hbox = new HBox(20, encryptBtn, decryptBtn);
        timeLabel = new Label();
        pathLabel = new Label();
        pathLabel.setMinWidth(300);
        timeLabel.setFont(new Font("Georgia", 14));
        pathLabel.setFont(new Font("Georgia", 14));
        encryptBtn.setDisable(true);
        decryptBtn.setDisable(true);
        pathLabel.setText("You have to choose file");


        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setPadding(new Insets(30, 30, 15, 30));

        grid.add(hbox, 0, 1);
        grid.add(chooseFileBtn, 1, 0);
        grid.add(keyInput, 0, 0);
        grid.add(timeLabel, 0, 3);
        grid.add(pathLabel, 0, 2);

        primaryStage.setScene(new Scene(grid, 500, 400));
        primaryStage.show();

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        Cipher cipher = Cipher.getInstance("AES");

        chooseFileBtn.setOnAction(event -> {
            file[0] = fileChooser.showOpenDialog(primaryStage);
            pathLabel.setText("File: " + file[0].getAbsolutePath());
            if(file[0]!=null){
                encryptBtn.setDisable(false);
                decryptBtn.setDisable(false);
            }
        });


        encryptBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                long startTime = System.currentTimeMillis();
                init("encrypt",primaryStage, file[0], keyInput);
                timeLabel.setText("Execution time (mm:ss:sss): "+getExecutionTime(startTime)+ " seconds");
            }
        });
        decryptBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                long startTime = System.currentTimeMillis();
                init("decrypt",primaryStage, file[0], keyInput);
                timeLabel.setText("Execution time (mm:ss:sss): "+getExecutionTime(startTime)+ " seconds");

            }
        });
    }

    private String getExecutionTime(long startTime){
        long finishTime = System.currentTimeMillis()-startTime;
        SimpleDateFormat format = new SimpleDateFormat("mm:ss:sss");
        String str = format.format(finishTime);
        return str;
    }

    private void init(String mode, Stage primaryStage, File file, TextField keyInput){
        String inputFilePath;
        String outputFilePath;
        String stringKey = keyInput.getText();
        MessageDigest sha;

        try {
            sha = MessageDigest.getInstance("SHA-1");
            byte[] decodedKey = stringKey.getBytes();
            decodedKey = sha.digest(decodedKey);
            decodedKey = Arrays.copyOf(decodedKey, 16);
            SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            //inputFilePath = file.getAbsolutePath();
            if(mode == "encrypt"){
                Encrypt.encrypt(Cipher.ENCRYPT_MODE, file, key);
            }
            else if(mode == "decrypt"){
                Encrypt.encrypt(Cipher.DECRYPT_MODE, file, key);
            }

            Encrypt.hashComparator(file);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
