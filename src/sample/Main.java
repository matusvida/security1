package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Encrypt/Decrypt tool");


        Button encryptBtn, decryptBtn;
        GridPane grid;

        grid = new GridPane();
        encryptBtn = new Button("Encrypt file");
        decryptBtn = new Button("Decrypt file");

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20, 20, 10, 20));

        grid.add(encryptBtn, 0, 0);
        grid.add(decryptBtn, 0, 1);

        primaryStage.setScene(new Scene(grid, 300, 275));
        primaryStage.show();


        encryptBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        decryptBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
