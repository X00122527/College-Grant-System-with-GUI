/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import static java.lang.String.format;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.*;

/**
 *
 * @author T450
 */
public class Student {

    private logger file;
    private String login;
    private double result;
    private int feePaid = 0;
    private int countGrant100 =0;
    private int countGrant75 =0;
    private int countGrant50 =0;
    private int countGrant0 = 0;
    private int numofStudentsGrantReceived;
    private int studentsAppliedForGrant;
    private double feesDue;
    private double feePaidbyGrant;
    private double totalgrantsPaid;
    private double totalnongrantsPaid;
    private final double GRANTMAX = 3000.00;
    private int mostStudentsGrant;
    private int calledAverage;
    private int called;
    
    public Student(String number) {
        login = number;
    }
    
    public String getLogin() {
        return login;
    }

    public int getFeePaid() {
        return feePaid;
    }

    public int getNumofStudentsGrantReceived() {
        return numofStudentsGrantReceived;
    }

    public int getCountGrant100() {
        return countGrant100;
    }

    public int getCountGrant75() {
        return countGrant75;
    }

    public int getCountGrant50() {
        return countGrant50;
    }

    public int getCountGrant0() {
        return countGrant0;
    }

    public double getFeesDue() {
        return feesDue;
    }

    public double getFeePaidbyGrant() {
        return feePaidbyGrant;
    }
    //x
    public void setFeePaidbyGrant(double feePaidbyGrant) {
        this.feePaidbyGrant = feePaidbyGrant;
    }

    public double getTotalgrantsPaid() {
        return totalgrantsPaid;
    }

    public double getTotalnongrantsPaid() {
        return totalnongrantsPaid;
    }
    //xx
    public void setTotalnongrantsPaid(double totalnongrantsPaid) {
        this.totalnongrantsPaid = totalnongrantsPaid;
    }

    public double getGRANTMAX() {
        return GRANTMAX;
    }

    public int getCalledAverage() {
        return calledAverage;
    }

    public void setCountGrant100(int countGrant100) {
        this.countGrant100 = countGrant100;
    }

    public void setCountGrant75(int countGrant75) {
        this.countGrant75 = countGrant75;
    }

    public void setCountGrant50(int countGrant50) {
        this.countGrant50 = countGrant50;
    }

    public void setCountGrant0(int countGrant0) {
        this.countGrant0 = countGrant0;
    }
    

    


    public double calculateAverage(double result1, double result2, double result3, double result4) {
        result = (result1 + result2 + result3 + result4) / 4;
        studentsAppliedForGrant++;
        calledAverage++;
        return result;
    }

    public double getResult() {
        return result;
    }

    public double convertTextToDouble(TextField t1) {
        String s = t1.getText();
        double value = Double.parseDouble(s);
        return value;
    }
    
    public double convertLabelToDouble(Label l1) {
        String s = l1.getText();
        double value = Double.parseDouble(s);
        return value;
    }

    public void calculateFee() {
        double average = getResult();
        if (average >= 80) {
            feePaid = 100;
            countGrant100++;
        }

        if (average >= 60 && average < 80) {
            feePaid = 75;
            countGrant75++;
        }

        if (average >= 40 && average < 60) {
            feePaid = 50;
            countGrant50++;
        }

        if (average < 40) {
            feePaid = 0;
            countGrant0++;
        }

        feesDue = ((GRANTMAX * (feePaid / 100.00)) - GRANTMAX) * (-1.00);
        feePaidbyGrant += (GRANTMAX - feesDue);
        numofStudentsGrantReceived = (countGrant100 + countGrant75 + countGrant50);
        totalgrantsPaid = totalgrantsPaid + feePaidbyGrant;
        totalnongrantsPaid = totalnongrantsPaid + feesDue;
        called++;
    }

    public int getCalled() {
        return called;
    }

   
    
    public int getStudentsAppliedForGrant() {
        return studentsAppliedForGrant;
    }
    
    public void checkCount(){
        if(getCountGrant100()>0){
            setCountGrant100(0);
        }else if(getCountGrant75()>0){
            setCountGrant75(0);
        }else if(getCountGrant50()>0){
            setCountGrant50(0);
        }else if(getCountGrant0()>0){
            setCountGrant0(0);
        }
    }
    
    public void checkFees(){
        if(getCalled()>0){
            setFeePaidbyGrant(0);
            setTotalnongrantsPaid(0);
        }
    }
    
    
/*
    public void print() {
        System.out.println("result " + result);
        System.out.println("fee paid " + feePaid);
        System.out.println("100% grant " + countGrant100);
        System.out.println("75% grant " + countGrant75);
        System.out.println("50% grant " + countGrant50);
        System.out.println("0% grant " + countGrant0);
        System.out.println("number of students who received grant " + numofStudentsGrantReceived);
        System.out.println("applied for grant " + studentsAppliedForGrant);
        System.out.println("fees due "+feesDue);
        System.out.println("feePaidbyGrant "+feePaidbyGrant);
        System.out.println("totalgrantsPaid" + totalgrantsPaid);
        System.out.println("totalnongrantsPaid "+totalnongrantsPaid);
        
    }
*/
}
