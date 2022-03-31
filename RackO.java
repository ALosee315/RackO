/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author warri
 */
import java.util.Scanner;
import java.util.Random;

public class RackO {

    static int[] plr1Rack = new int[10];
    static int[] plr2Rack = new int[10];
    static int[] deckOCards = new int[61];
    static int[] discardPile = new int[40];
    static String plr1Name;
    static String plr2Name;
    static int discard;
    static int draw;
    static Random rand = new Random();
    static Scanner input = new Scanner(System.in);
    static int turn = 1;

    public static void removeCard(int selectedCard) {
        for (int i = selectedCard; i < deckOCards.length - 1; i++) { //Remove card from array
            deckOCards[i] = deckOCards[i + 1];
            //System.out.println(deckOCards[i]);
        }

        System.out.println("");
    }

    public static void checkArray() {
        for (int i = 0; i < deckOCards.length; i++) { 
            System.out.println(deckOCards[i]);
        }

    }

    public static int selectCard() {
        int selectedCard = rand.nextInt(deckOCards.length);
        if (deckOCards[selectedCard] == 0) {
            while (deckOCards[selectedCard] == 61 || deckOCards[selectedCard] == 0) {
                selectedCard = rand.nextInt(deckOCards.length);
            }
        }
        if (deckOCards[selectedCard] != 0) {
            return selectedCard;
        } else {
            System.out.println("Error!!!");
            return -1;
        }

    }

    public static void checkForShuffle() {
        if (deckOCards[0]==0) {
            deckOCards = new int[40];
            for (int i = 0; i < discardPile.length; i++) { //Generate deck, 1-40
                deckOCards[i] = discardPile[i]; //Transfer Discard Pile into reshuffled draw deck
            }
            discardPile = new int[40]; //Clear Discard Pile
        }
    }

    public static void insertDiscard(int discard) {
        for (int i = discardPile.length - 1; i > 0; i--) {
            discardPile[i] = discardPile[i - 1];
        }
        discardPile[0] = discard;

    }

    public static void dealCards() {
        for (int i = 0; i < 60; i++) { //Generate deck, 1-60
            deckOCards[i] = i + 1;
        }

        for (int i = 0; i < 10; i++) {
            int selectedCard1 = selectCard();
            plr1Rack[i] = deckOCards[selectedCard1];
            removeCard(selectedCard1);
            System.out.println(selectedCard1);
            int selectedCard2 = selectCard();
            plr2Rack[i] = deckOCards[selectedCard2];
            removeCard(selectedCard2);
            //System.out.println(plr1Rack[i]+"\t"+plr2Rack[i]);
        }
        int selectedCard3 = selectCard();
        discard = deckOCards[selectedCard3];
        removeCard(selectedCard3);
        int selectedCard4 = selectCard();
        draw = deckOCards[selectedCard4];
        removeCard(selectedCard4);
    }

    public static void showDeck1() { //Method for revealing Rack 1
        System.out.println("\n--------" + plr1Name + "'s-Rack----------");
        System.out.println("Points\tSlot\tCard");
        for (int i = 0; i < 10; i++) {
            System.out.println((50 - 5 * i) + "\t|" + i + "|\t" + plr1Rack[i]);
        }
        System.out.println("--------------------------------");
        System.out.println("Discard: " + discard);
        System.out.println("--------------------------------");
    }

    public static void showDeck2() { //Method for revealing Rack 2
        System.out.println("\n--------" + plr2Name + "'s-Rack----------");
        System.out.println("Points\tSlot\tCard");
        for (int i = 0; i < 10; i++) {
            System.out.println((50 - 5 * i) + "\t|" + i + "|\t" + plr2Rack[i]); //Format the table
        }
        System.out.println("--------------------------------");
        System.out.println("Discard: " + discard);
        System.out.println("--------------------------------");

    }

    public static int checkForWin() {
        int winner = 0;
        if (turn == 1) {
            for (int i = 9; i >= 0; i--) { //Runs backwards through the table making sure that the rack is increasing upwards
                if (i > 0) {
                    if (plr1Rack[i] > plr1Rack[i - 1]) {
                        return (winner);
                    }

                }
            }
            winner = 1;
            return (winner);
        } else {
            for (int i = 9; i >= 0; i--) {
                if (i > 0) {
                    if (plr2Rack[i] < plr2Rack[i - 1]) {
                        return (winner);
                    }

                }
            }
            winner = 2;
            return (winner);
        }

    }

    public static void main(String[] args) {

        int cardHolding;
        int userChoice;

        dealCards();
        //System.out.println("Discard: "+discard);
        System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println("Welcome to two player RackO!");
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
        System.out.println("Player 1's Name:");
        plr1Name = input.nextLine();
        System.out.println("Player 2's Name:");
        plr2Name = input.nextLine();
        while (true) {
            if (turn == 1) {
                System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                System.out.println(plr1Name + " is up! Here is your rack:");
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                showDeck1();
                System.out.println("Choose 1 to take discard, or 2 to draw new card");

                userChoice = input.nextInt();
                if (userChoice == 1) {
                    cardHolding = discard;
                } else if (userChoice == 3) {
                    checkArray(); //TEST
                    cardHolding = draw;
                } else {
                    checkForShuffle();
                    int selectedCard = selectCard();
                    draw = deckOCards[selectedCard]; //Recreate a random card to draw
                    removeCard(selectedCard);
                    cardHolding = draw;
                    System.out.println("You drew " + draw);
                }

                System.out.println("Choose a slot number, 0-9, -1 to discard");
                userChoice = input.nextInt();
                if (userChoice == -1) { //Discard the new draw
                    insertDiscard(discard);
                    discard = cardHolding;
                
                } else {
                    insertDiscard(discard);
                    discard = plr1Rack[userChoice];
                    plr1Rack[userChoice] = cardHolding;
                }

                System.out.println("Your new rack!");
                showDeck1();
                checkForWin();
                if (checkForWin() == 1) {
                    System.out.println(plr1Name + " wins!");
                    break;
                } else if (checkForWin() == 2) {
                    System.out.println(plr2Name + " wins!");
                    break;
                } else {
                    turn = 2;
                }

                //End of player 1's turn
            }
            if (turn == 2) {
                System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                System.out.println(plr2Name + " is up! Here is your rack:");
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
                showDeck2();
                System.out.println("Choose 1 to take discard, or 2 to draw new card");

                userChoice = input.nextInt();
                if (userChoice == 1) {
                    cardHolding = discard;
                } else if (userChoice == 3) {
                    checkArray(); //TEST
                    cardHolding = draw;
                } else {
                    checkForShuffle();
                    int selectedCard = selectCard();
                    draw = deckOCards[selectedCard];  //Recreate a random card to draw
                    removeCard(selectedCard);
                    cardHolding = draw;
                    System.out.println("You drew " + draw);
                }

                System.out.println("Choose a slot number, 0-9, -1 to discard");
                userChoice = input.nextInt();
                if (userChoice == -1) { //Discard the new drawn card
                    insertDiscard(discard);
                    discard = cardHolding;
                } else {
                    insertDiscard(discard);
                    discard = plr2Rack[userChoice];
                    plr2Rack[userChoice] = cardHolding;
                }

                System.out.println("Your new rack!");
                showDeck2();
                checkForWin();
                if (checkForWin() == 1) {
                    System.out.println(plr1Name + " wins!");
                    break;
                } else if (checkForWin() == 2) {
                    System.out.println(plr2Name + " wins!");
                    break;
                } else {
                    turn = 1;
                }

                //End of player 2's turn
            }
        }
    }
}
