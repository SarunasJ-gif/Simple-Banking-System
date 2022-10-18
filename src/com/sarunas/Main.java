package com.sarunas;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> cardNumber = new ArrayList<>();
        ArrayList<String> cardPIN = new ArrayList<>();
        int balance = 0;
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
                    String newCard = "400000";
                    Random random = new Random();
                    for (int i = 0; i < 10; i++) {
                        newCard += random.nextInt(9);
                    }
                    if (cardNumber.contains(newCard)) {
                        System.out.println("Card number exists");
                    } else {
                        cardNumber.add(newCard);
                    }
                    String pin = "";
                    for (int i = 0; i < 4; i++) {
                        pin += random.nextInt(9);
                    }
                    cardPIN.add(pin);
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
                    if (cardNumber.contains(existingCardNumber)) {
                        System.out.println("Enter your PIN:");
                        System.out.print(">");
                        int cardIndex = cardNumber.indexOf(existingCardNumber);
                        String cardsPin = scanner.nextLine();
                        if (cardsPin.equals(cardPIN.get(cardIndex))) {
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
                                        bankSystem = false;
                                }
                            }
                        } else {
                            System.out.println();
                            System.out.println("Wrong card number or PIN!");
                        }
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



//    public static ArrayList<String> creditCard() {
//        ArrayList<String> cardNumber = new ArrayList<>();
//        String newCard = "400000";
//        Random random = new Random();
//        for (int i = 0; i < 10; i++) {
//            newCard += random.nextInt(9);
//        }
//        System.out.println(newCard);
//        if (cardNumber.contains(newCard)) {
//            System.out.println("Card number exists");
//        } else {
//            System.out.println("Created new card");
//            cardNumber.add(newCard);
//        }
//        return cardNumber;
//    }
//
//    public static ArrayList<String> createPIN() {
//        ArrayList<String> cardPIN = new ArrayList<>();
//        Random random = new Random();
//        String pin = "";
//        for (int i = 0; i < 4; i++) {
//            pin += random.nextInt();
//        }
//        cardPIN.add(pin);
//        return cardPIN;
//    }
//
//
//    public static boolean login() {
//        ArrayList<String> creditCard = creditCard();
//        ArrayList<String> cardPIN = createPIN();
//        return true;
//    }
}
