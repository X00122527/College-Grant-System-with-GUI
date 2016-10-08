/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.AlertBox.display;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author T450
 */
public class Javafx extends Application {

    Stage window, window2;
    GridPane mainGrid;
    Scene scene, scene2, scene3;
    Student s1;
    logger log = new logger();
    logger record = new logger();

    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.checkingFiles(); // check if dictionary and files exist and create them
        window = primaryStage;
        window.setTitle("Login to get access to application");

        mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(10, 10, 10, 10));
        mainGrid.setHgap(10);
        mainGrid.setVgap(8);

        Label logLabel = new Label();
        logLabel.setText("Login:");
        mainGrid.setConstraints(logLabel, 0, 0);

        TextField logField = new TextField();
        logField.setPromptText("Enter your login");
        mainGrid.setConstraints(logField, 1, 0);

        Label passLabel = new Label();
        passLabel.setText("Password:");
        mainGrid.setConstraints(passLabel, 0, 1);

        TextField passField = new TextField();
        passField.setPromptText("Enter your password");
        mainGrid.setConstraints(passField, 1, 1);

        Button logButton = new Button("Log in");
        mainGrid.setConstraints(logButton, 1, 2);
        logButton.setOnAction(e -> {
            try {
                root(logField, passField);
            } catch (IOException ex) {
                Logger.getLogger(Javafx.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );

        Button regButton = new Button("Register");
        mainGrid.setConstraints(regButton, 2, 2);

        regButton.setOnAction(e -> {
            try {
                log.register(logField.getText(), passField.getText());
                // or there V

            } catch (IOException ex) {
                Logger.getLogger(Javafx.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );

        mainGrid.getChildren().addAll(logLabel, logField, passLabel, passField, logButton, regButton);

        Scene scene = new Scene(mainGrid);

        window.setScene(scene);
        window.show();

    }

    private void root(TextField login, TextField pass) throws IOException {
        if (log.logIn(login.getText(), pass.getText())) {
            s1 = new Student(login.getText());
            record.createAccountFile(s1.getLogin());// here <-
            window.close();
            window2 = new Stage();
            window2.setTitle("You're logged as:  " + s1.getLogin());
            window2.setHeight(240);
            window2.setWidth(400);
            createCalcContent();
            window2.show();

        } else {
            AlertBox.logginError("Your username or password is wrong!");

        }
    }

    private Scene createCalcContent() throws IOException {

        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(10, 10, 10, 10));
        grid1.setHgap(10);
        grid1.setVgap(8);

        TextField textArray[] = new TextField[4];

        Label rLabel1 = new Label("Software development result ->");
        rLabel1.setMinWidth(Region.USE_PREF_SIZE);
        grid1.setConstraints(rLabel1, 0, 1);
        textArray[0] = new TextField();
        grid1.setConstraints(textArray[0], 2, 1);

        Label rLabel2 = new Label("Web and User Experience result ->");
        rLabel2.setMinWidth(Region.USE_PREF_SIZE);
        grid1.setConstraints(rLabel2, 0, 2);
        textArray[1] = new TextField();
        grid1.setConstraints(textArray[1], 2, 2);

        Label rLabel3 = new Label("Computer Architecture result ->");
        rLabel3.setMinWidth(Region.USE_PREF_SIZE);
        grid1.setConstraints(rLabel3, 0, 3);
        textArray[2] = new TextField();
        grid1.setConstraints(textArray[2], 2, 3);

        Label rLabel4 = new Label("Discrete Mathematics result ->");
        rLabel4.setMinWidth(Region.USE_PREF_SIZE);
        grid1.setConstraints(rLabel4, 0, 4);
        textArray[3] = new TextField();
        grid1.setConstraints(textArray[3], 2, 4);

        Button calcButton = new Button("Calculate!");
        grid1.setConstraints(calcButton, 0, 6);

        Button loadButton = new Button("Load data");
        loadButton.setMinWidth(Region.USE_PREF_SIZE);
        loadButton.setOnAction(e -> {
            String data[];
            try {
                data = record.loadData(s1.getLogin());
                for (int i = 0; i < 4; i++) {
                    textArray[i].clear(); // so when you press load twice, it doesn't bug
                    textArray[i].setText(data[i]);

                }
            } catch (IOException ex) {
                Logger.getLogger(Javafx.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        grid1.setConstraints(loadButton, 2, 6);

        Label resultLabel = new Label();
        
        calcButton.setOnAction(e -> {
            resultLabel.setText("");
            double r1, r2, r3, r4;
            r1 = s1.convertTextToDouble(textArray[0]);
            r2 = s1.convertTextToDouble(textArray[1]);
            r3 = s1.convertTextToDouble(textArray[2]);
            r4 = s1.convertTextToDouble(textArray[3]);
            double result = s1.calculateAverage(r1, r2, r3, r4);
            String sresult = Double.toString(result);
            s1.checkCount();
            s1.checkFees();
            s1.calculateFee();
            resultLabel.setText("The average of 4 results is " + sresult);
            resultLabel.setMinWidth(Region.USE_PREF_SIZE);
            record.writeToDataFile(s1.getLogin());

            if (record.isEmpty(s1.getLogin())) {
                try {
                    record.saveRecords(r1, r2, r3, r4); // do we need a try?
                    System.out.println("saving records..");

                } catch (IOException ex) {
                    Logger.getLogger(Javafx.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                display("Make changes?", "There is already saved data\ndo you want to overwrite them?");
                if (AlertBox.isButton() == true) {
                    try {
                        record.kill(s1.getLogin());
                        record.saveRecords(r1, r2, r3, r4);
                        record.writeToDataFile(s1.getLogin());

                        record.saveStats(s1.getCountGrant100(), s1.getCountGrant75(), s1.getCountGrant50(), s1.getCountGrant0());
                    } catch (IOException ex) {
                        Logger.getLogger(Javafx.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

        });
        
        grid1.setConstraints(resultLabel, 0, 5);
        
        grid1.getChildren().addAll(rLabel1, rLabel2, rLabel3, rLabel4, textArray[0], textArray[1], textArray[2], textArray[3], calcButton, resultLabel,loadButton);

        BorderPane borderpane = new BorderPane();
        borderpane.setTop(createTop());
        borderpane.setCenter(grid1);
        scene = new Scene(borderpane);
        window2.setScene(scene);
        return scene;
    }

    private Scene createStatistic() throws IOException {
        GridPane grid3 = new GridPane();
        Text title3 = new Text("*** GRANT STATISTICS ***");
        title3.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        grid3.setConstraints(title3, 1, 0);
        int[] data = log.goThruFiles();
        Label labelArray[] = new Label[4];
        labelArray[0] = new Label(data[0] + " students received a 100% of fees paid.");
        grid3.setConstraints(labelArray[0], 1, 1);
        labelArray[1] = new Label(data[1] + " students received a 75% of fees paid.");
        grid3.setConstraints(labelArray[1], 1, 2);
        labelArray[2] = new Label(data[2] + " students received a 50% of fees paid.");
        grid3.setConstraints(labelArray[2], 1, 3);
        labelArray[3] = new Label(data[3] + " students didn't received grant.");
        grid3.setConstraints(labelArray[3], 1, 4);

        grid3.setVgap(4);
        grid3.setHgap(3);
        grid3.setPadding(new Insets(window2.getHeight() * 0.1, window2.getWidth() * 0.2, window2.getHeight() * 0.1, window2.getWidth() * 0.2));
        grid3.getChildren().addAll(labelArray[0], labelArray[1], labelArray[2], labelArray[3], title3);

        BorderPane borderpane3 = new BorderPane();
        borderpane3.setTop(createTop());
        borderpane3.setCenter(grid3);

        scene3 = new Scene(borderpane3);

        return scene3;
    }

    private Scene createInformation() {
        GridPane grid2 = new GridPane();
        Text title2 = new Text("*** FEE STATISTICS ***");
        title2.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        grid2.setConstraints(title2, 1, 0);
        Label l1 = new Label("Fees paid by grants: " + s1.getFeePaidbyGrant());
        grid2.setConstraints(l1, 1, 1);
        Label l2 = new Label("Fees you need to pay : " + s1.getTotalnongrantsPaid());
        grid2.setConstraints(l2, 1, 2);

        /* TOO LAZY TO FINISH IT 
         Label l3 = new Label("Students who received grant: " + s1.getNumofStudentsGrantReceived());
         grid2.setConstraints(l3, 1, 3);
         Label l4 = new Label("Students applied for grant: " + s1.getNumofStudentsGrantReceived());
         grid2.setConstraints(l4, 1, 4);
         */
        grid2.getChildren().addAll(l1, l2, title2);
        grid2.setVgap(4);
        grid2.setHgap(3);
        grid2.setPadding(new Insets(window2.getHeight() * 0.1, window2.getWidth() * 0.2, window2.getHeight() * 0.1, window2.getWidth() * 0.2));
        BorderPane borderpane2 = new BorderPane();
        borderpane2.setTop(createTop());
        borderpane2.setCenter(grid2);
        scene2 = new Scene(borderpane2);

        return scene2;
    }

    private HBox createTop() {
        HBox top = new HBox();
        Button calcButton = new Button("Calculate");
        Button statButton = new Button("Statistics");
        Button infoButton = new Button("Information");
        Button exitButton = new Button("Exit");
        calcButton.setOnAction(e -> {
            try {
                window2.setScene(createCalcContent());

            } catch (IOException ex) {
                Logger.getLogger(Javafx.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        statButton.setOnAction(e -> {
            try {
                window2.setScene(createStatistic());
            } catch (IOException ex) {
                Logger.getLogger(Javafx.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        infoButton.setOnAction(e -> window2.setScene(createInformation()));

        exitButton.setOnAction(e -> window2.close());

        top.getChildren().addAll(calcButton, statButton, infoButton, exitButton);

        return top;
    }
}
