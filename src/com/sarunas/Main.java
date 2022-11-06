package com.sarunas;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        CreditCardsDatabase cardDatabase = new CreditCardsDatabase();
        cardDatabase.createDB();
        Connection conn = connect();
        Random random = new Random();
        random.setSeed(10);
        int zeroBalance = 0;
        boolean bankSystem = true;
        while (bankSystem) {
            System.out.println();
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            System.out.print(">");

            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":

                    String newCard = newCardNumber(random);
                    String pin = "";
                    for (int i = 0; i < 4; i++) {
                        pin += random.nextInt(0, 10);
                    }
                    cardDatabase.insertData(conn, newCard, pin, zeroBalance);
                    System.out.println();
                    System.out.println("Your card has been created");
                    System.out.println("Your card number:");
                    System.out.println(newCard);
                    System.out.println("Your card PIN:");
                    System.out.println(pin);
                    break;
                case "2":
                    System.out.println();
                    System.out.println("Enter your card number:");
                    System.out.print(">");
                    String existingCardNumber = scanner.nextLine();

                    System.out.println("Enter your PIN:");
                    System.out.print(">");
                    String cardsPin = scanner.nextLine();
                    String pinInDatabase = cardDatabase.getPin(conn, existingCardNumber);
                    if (cardsPin.equals(pinInDatabase)) {
                        System.out.println();
                        System.out.println("You have successfully logged in!");
                        boolean login = true;
                        while (login) {
                            System.out.println();
                            System.out.println("1. Balance");
                            System.out.println("2. Log out");
                            System.out.println("0. Exit");
                            System.out.print(">");
                            String loginChoice = scanner.nextLine();
                            switch (loginChoice) {
                                case "1":
                                    int balance = cardDatabase.getBalance(conn, existingCardNumber);
                                    System.out.println();
                                    System.out.println("Balance: " + balance);
                                    break;
                                case "2":
                                    System.out.println();
                                    System.out.println("You have successfully logged out!");
                                    login = false;
                                    break;
                                case "0":
                                    System.out.println();
                                    System.out.println("Bye!");
                                    login = false;
                                    bankSystem = false;
                            }
                        }
                    } else {
                        System.out.println();
                        System.out.println("Wrong card number or PIN!");
                    }
                    break;
                case "0":
                    System.out.println();
                    System.out.println("Bye!");
                    bankSystem = false;
                    break;
            }
        }
    }

    public static String newCardNumber(Random rand) {
        String newCard = "400000";
        // Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            newCard += rand.nextInt(0, 10);
        }
        String[] array = newCard.split("");

        int[] numbers = new int[15];
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt(array[i]);
            if ((i + 1) % 2 != 0) {
                numbers[i] *= 2;
            }
            if (numbers[i] > 9) {
                numbers[i] -= 9;
            }
            sum += numbers[i];
        }

        while (true) {
            int lastNumber = rand.nextInt(0, 10);
            if ((sum + lastNumber) % 10 == 0) {
                newCard += lastNumber;
                break;
            }
        }
        return newCard;
    }

    private static Connection connect() {
        String url = "jdbc:sqlite:cards.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}





