/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import javafx.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Tymek
 */
public class AlertBox {

    static boolean buttonValue;

    public static boolean isButton() {
        return buttonValue;
    }

    public static void display(String title, String message) {
        int choice;
        Stage window = new Stage();

        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        Label msg = new Label();
        msg.setText(message);

        Button b1 = new Button("Yes");
        b1.setOnAction(e -> {
            buttonValue = true;
            window.close();

        });

        Button b2 = new Button("No");
        b2.setOnAction(e -> {
            buttonValue = false;
            window.close();
        });

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(msg, b1, b2);
        window.setWidth(300);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        /*
         if(b1.isPressed()){
         System.out.println("button yes was pressed");
         return true;
            
         }else{
         System.out.println("button no was pressed");
         return false;
         } // <- fix
         */

    }

    public static void logginError(String message) {
        Stage window = new Stage();

        window.setTitle("Error");
        window.initModality(Modality.APPLICATION_MODAL);
        Label msg = new Label();
        msg.setText(message);

        Button b1 = new Button("OK");
        b1.setOnAction(e -> window.close());

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(msg, b1);
        window.setWidth(300);
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }

}
