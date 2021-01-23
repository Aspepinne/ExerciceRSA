package com.aspepinne;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileTools {

    public static String readFile(String path) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("Encrypted/"+path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert sc != null;
        String text = sc.useDelimiter("\\A").next();
        sc.close();
        return text;
    }

    public static void writeToFile(String fileName, String encryptedMessage) {
        try {
            FileWriter fw = new FileWriter("Encrypted/" + fileName);
            fw.write(encryptedMessage);
            fw.close();
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
    }
}