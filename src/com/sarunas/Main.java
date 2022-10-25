package com.sarunas;


import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        random.setSeed(10);
        HashMap<String, String> creditCard = new HashMap<>();
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

                    String newCard = newCardNumber(random);
                    if (creditCard.containsKey(newCard)) {
                        System.out.println("Card number exists");
                    } else {
                        String pin = "";
                        for (int i = 0; i < 4; i++) {

                            pin += random.nextInt(0, 10);
                        }
                        creditCard.put(newCard, pin);
                        System.out.println();
                        System.out.println("Your card has been created");
                        System.out.println("Your card number:");
                        System.out.println(newCard);
                        System.out.println("Your card PIN:");
                        System.out.println(pin);
                    }
                    break;
                case "2":
                    System.out.println();
                    System.out.println("Enter your card number:");
                    System.out.print(">");
                    String existingCardNumber = scanner.nextLine();
                    if (creditCard.containsKey(existingCardNumber)) {
                        System.out.println("Enter your PIN:");
                        System.out.print(">");
                        String cardsPin = scanner.nextLine();
                        if (cardsPin.equals(creditCard.get(existingCardNumber))) {
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

