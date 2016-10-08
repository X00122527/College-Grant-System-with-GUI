/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import java.nio.*;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.AlertBox.display;
import static javafx.AlertBox.logginError;
import javafx.scene.shape.Shape;

/**
 *
 * @author Tymek
 */
public class logger {

    private Student s1;
    private Scanner x;
    private BufferedReader y, recordOut;
    private BufferedWriter z, recordIn;
    private PrintWriter recordIn2;
    private String path = System.getProperty("user.home") + "\\Desktop";

    public void checkingFiles() throws FileNotFoundException {
        new File(path + "\\grant_app").mkdirs(); // ? not sure
        new File(path + "\\grant_app\\data").mkdirs();
        File f3 = new File(path + "\\grant_app\\accounts.txt");
        
        if (f3.exists() == false) {
            PrintWriter y = new PrintWriter(f3);
            y.append("admin:admin");
            y.close();
        }

    }

    public void createAccountFile(String name) throws FileNotFoundException, IOException {
        File file = new File(path + "\\grant_app\\data\\" + name + ".txt");
        if (file.exists() == false) {
            try (PrintWriter printwriter = new PrintWriter(file)) {
                System.out.println("Account was created.");
                populateNewFile(name);
            }

        } 

    }

    public void recordIn2(String fileName) {
        try {
            recordIn2 = new PrintWriter(path + "\\grant_app\\data\\" + fileName + ".txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(logger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void populateNewFile(String name) throws IOException {
        System.out.println("populating..." + name);

        writeToDataFile(name);
        for (int i = 0; i < 8; i++) {
            recordIn.append("0");
            recordIn.newLine();
        }
        recordIn.close();

    }

    public void openAccountsFile() {
        try {
            z = new BufferedWriter(new FileWriter(path + "\\grant_app\\accounts.txt", true));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void openReadToFile() {
        try {
            y = new BufferedReader(new FileReader(path + "\\grant_app\\accounts.txt"));
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public Boolean logIn(String loginIn, String passwordIn) throws IOException {
        openReadToFile();

        if (!loginIn.equals("") || !passwordIn.equals("")) {
            while (y.ready()) {
                String crap = y.readLine(); 
                boolean check = crap.contains(loginIn); 
                if (check == true) {
                    String login = (String) (crap.subSequence(0, crap.indexOf(":"))); // stores login
                    String password = (crap.substring(crap.indexOf(":") + 1)); // stores password
                    if (loginIn.equals(login) && passwordIn.equals(password)) { // 
                        return true; 
                    } else {
                        closeReadingToFile();
                    }
                }
            }
        }

        return false;
    }

    public void register(String loginIn, String passwordIn) throws IOException {
        // check if login is already used
        openAccountsFile();
        openReadToFile();
        while (y.ready()) {
            if (loginIn.equals("") || passwordIn.equals("")) {
                AlertBox.logginError("Both registration fields should be filled.");
                break;
            }
            if (logIn(loginIn, passwordIn) == false && !loginIn.equals("") && !passwordIn.equals("")) {
                z.newLine();
                z.append(loginIn);
                z.append(":");
                z.append(passwordIn);
                break;
            }
            if (logIn(loginIn, passwordIn) == true) {
                AlertBox.logginError("Account with that username already exist.");
                break;
            }

        }
        closeWritingToFile();
        closeReadingToFile();

    }

    public void closeWritingToFile() throws IOException {
        z.close();
    }

    public void closeReadingToFile() throws IOException {
        y.close();
    }

    public void saveRecords(double result1, double result2, double result3, double result4) throws IOException {
        String r1 = String.valueOf(result1);
        String r2 = String.valueOf(result2);
        String r3 = String.valueOf(result3);
        String r4 = String.valueOf(result4);
        System.out.println("save method executed.");
        recordIn.append(r1);
        recordIn.newLine();
        recordIn.append(r2);
        recordIn.newLine();
        recordIn.append(r3);
        recordIn.newLine();
        recordIn.append(r4);
        recordIn.newLine();
        closeRecordFile();
    }

    public void saveStats(int result1, int result2, int result3, int result4) throws IOException {
        String r1, r2, r3, r4, r5;

        r1 = String.valueOf(result1);
        r2 = String.valueOf(result2);
        r3 = String.valueOf(result3);
        r4 = String.valueOf(result4);
        recordIn.append(r1);
        recordIn.newLine();
        recordIn.append(r2);
        recordIn.newLine();
        recordIn.append(r3);
        recordIn.newLine();
        recordIn.append(r4);
        recordIn.newLine();
        closeRecordFile();
    }

    public int[] loadStats() throws IOException {
        openReadToFile();

        int files = new File(System.getProperty("user.home") + "\\Desktop" + "\\grant_app\\data").list().length;
        String[] fileNames = new String[files];
        System.out.println(files);
        int statistics[] = new int[8];

        for (int i = 0; i < files; i++) {
            int index = y.readLine().indexOf(":");
            fileNames[i] = (String) y.readLine().subSequence(0, index);
            System.out.println(fileNames[i] + index);
        }
        //after we got names of files
        for (int i = 0; i < files; i++) {
            openDataFile(fileNames[i]); // remove .txt in openDataFile
            // getting 5th line
            for (int j = 0; j < 8; j++) {
                statistics[j] += recordOut.read();
            }

        }

        return statistics;
    }

    public int[] goThruFiles() throws IOException {
        openReadToFile(); // get all names
        int files = new File(System.getProperty("user.home") + "\\Desktop" + "\\grant_app\\data").list().length;
        openReadToFile();
        String[] names = new String[files];
        File folder = new File(System.getProperty("user.home") + "\\Desktop" + "\\grant_app\\data");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            names[i] = (listOfFiles[i]).getName();
        }
        int ar[] = new int[4];
        for (int i = 0; i < files; i++) {
            String line5 = Files.readAllLines(Paths.get(path + "\\grant_app\\data\\" + names[i])).get(4);
            String line6 = Files.readAllLines(Paths.get(path + "\\grant_app\\data\\" + names[i])).get(5);
            String line7 = Files.readAllLines(Paths.get(path + "\\grant_app\\data\\" + names[i])).get(6);
            String line8 = Files.readAllLines(Paths.get(path + "\\grant_app\\data\\" + names[i])).get(7);
            ar[0] += Integer.parseInt(line5);
            ar[1] += Integer.parseInt(line6);
            ar[2] += Integer.parseInt(line7);
            ar[3] += Integer.parseInt(line8);
        }
        return ar;
    }

    public void writeToDataFile(String name) {
        try {
            recordIn = new BufferedWriter(new FileWriter(path + "\\grant_app\\data\\" + name + ".txt", true));
        } catch (Exception e) {
            System.out.println("couldn't file file");
        }
    }

    public void openDataFile(String name) {
        try {
            recordOut = new BufferedReader(new FileReader(path + "\\grant_app\\data\\" + name + ".txt")); // at the end -> + ".txt"
        } catch (Exception e) {
            System.out.println("Fail.");
        }

    }

    public boolean isEmpty(String fileName) {
        openDataFile(fileName);
        long lines = recordOut.lines().count();
        if (lines == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param name
     * @throws IOException
     */
    public void kill(String name) throws IOException {
        recordIn2(name);
        recordIn2.write("");
        recordIn2.flush();
        recordIn2.close();
    }

    public String[] loadData(String fileName) throws IOException {
        openDataFile(fileName);
        System.out.println("ready?:" + recordOut.ready());
        int boo = (int) recordOut.lines().count();
        openDataFile(fileName);
        System.out.println("boo: " + boo);
        String data[] = new String[boo];
        System.out.println("ready?:" + recordOut.ready());
        while (recordOut.ready()) {

            for (int i = 0; i < boo; i++) {
                data[i] = recordOut.readLine();
            }
        }
        return data;
    }

    public void closeRecordFile() throws IOException {
        recordIn.close();
    }

}
