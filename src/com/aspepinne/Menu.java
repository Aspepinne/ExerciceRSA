package com.aspepinne;

import java.util.Scanner;
import static com.aspepinne.KeyTools.keyGen;
import static com.aspepinne.KeyTools.readKey;
import static com.aspepinne.Encryption.*;
import static com.aspepinne.FileTools.*;

public class Menu {

    private static int menuInput = 0;
    private static Scanner sc;
    static KeyPair publicKey;
    static KeyPair privateKey;

    public static void menuController() {
        sc = new Scanner(System.in);
        menuInput = sc.nextInt();
    }

    public static String textInput() {
        sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static void mainMenu() {

        System.out.println("-------------------------");
        System.out.println("------- Main Menu -------");
        System.out.println("-------------------------");
        System.out.println("1. Generate keys");
        System.out.println("2. Encryption");
        System.out.println("3. Decryption");
        System.out.println("9. Exit");

        while (true) {
            System.out.print("> ");
            menuController();
            switch (menuInput) {
                case 1:
                    keyGenScreen();
                    break;
                case 2:
                    encryptionMenu();
                    break;
                case 3:
                    decryptionMenu();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid input please try again");
                    break;
            }
        }
    }

    public static void keyGenScreen() {
        System.out.println("-------------------------");
        System.out.println("----- Key Generator -----");
        System.out.println("-------------------------");
        System.out.println("Enter /exit to go back");
        System.out.println("Enter name of user: ");
        String fileName = textInput();
        if (fileName.equals("/exit")) mainMenu();
        System.out.println("Loading...");
        keyGen(fileName);
        mainMenu();
    }

    public static KeyPair loadKey(int controller) {

        System.out.println("Enter name of user: ");
        String userName = textInput();

        switch (controller) {
            case 1 -> {
                System.out.println("Loading public key...");
                publicKey = readKey(userName + "_pub.key");
                return publicKey;
            }
            case 2 -> {
                System.out.println("Loading private key...");
                privateKey = readKey(userName + "_priv.key");
                return privateKey;
            }
        }
        return null;
    }

    public static void encryptionMenu() {
        String encrypted = "";
        System.out.println("-------------------------");
        System.out.println("---- Encryption Menu ----");
        System.out.println("-------------------------");
        System.out.println("1. String");
        System.out.println("2. File");
        System.out.println("9. Back to main menu");

        while (true) {
            System.out.print("> ");
            menuController();
            switch (menuInput) {
                case 1 -> {
                    publicKey = loadKey(1);
                    System.out.println("Enter a message to encrypt: ");
                    System.out.print("> ");
                    String messageString = textInput();
                    encrypted = encrypt(messageString, publicKey);
                    System.out.println(encrypted);
                    System.out.println();
                    System.out.println("Do you want to save the encrypted string to a file?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    System.out.print("> ");
                    menuController();
                    switch (menuInput) {
                        case 1 -> {
                            System.out.print("> ");
                            System.out.println("Enter a name for the encrypted file:");
                            String fileName = textInput() + ".enc";
                            writeToFile(fileName, encrypted);
                            System.out.println(fileName + " Created.");
                        }
                        case 2 -> System.out.println("Encrypted string not saved.");

                        default -> System.out.println("Invalid input encrypted string not saved.");
                    }
                    encryptionMenu();
                }
                case 2 -> {
                    publicKey = loadKey(1);
                    System.out.println("Enter the name of the file: ");
                    System.out.print("> ");
                    String fileName = textInput();
                    String messageFileName = (fileName + ".txt");
                    String encryptedFileName = (fileName + ".enc");
                    String textFromFile = readFile(messageFileName);
                    String encryptedFileText = encrypt(textFromFile, publicKey);
                    writeToFile(encryptedFileName, encryptedFileText);
                    System.out.println(encryptedFileName + " Created");
                    encryptionMenu();
                }
                case 9 -> mainMenu();
                default -> System.out.println("Invalid input please try again");
            }
        }
    }

    public static void decryptionMenu() {
        String decrypted = "";
        System.out.println("-------------------------");
        System.out.println("---- Decryption Menu ----");
        System.out.println("-------------------------");
        System.out.println("1. String");
        System.out.println("2. File");
        System.out.println("9. Back to main menu");

        while (true) {
            System.out.print("> ");
            menuController();
            switch (menuInput) {
                case 1:
                    privateKey = loadKey(2);
                    decrypted = decrypt(textInput(), privateKey);
                    System.out.println("The decrypted message is:\n" + decrypted);
                    decryptionMenu();
                    break;
                case 2:
                    privateKey = loadKey(2);
                    System.out.println("Enter the name of the file to decrypt: ");
                    System.out.print("> ");
                    String encryptedFileName = textInput() + ".enc";
                    decrypted = decrypt(readFile(encryptedFileName), privateKey);
                    System.out.println("The decrypted message is:\n" + decrypted);
                    decryptionMenu();
                    break;
                case 9:
                    mainMenu();
                    break;
                default:
                    System.out.println("Invalid input please try again");
                    break;
            }
        }
    }
}