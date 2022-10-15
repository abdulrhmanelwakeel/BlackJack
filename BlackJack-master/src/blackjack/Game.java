package blackjack;

import java.util.Random;
import java.util.Scanner;

public class Game {
    static int maxScore = 0;

    Player[] players = new Player[4];
    Card[] cards = new Card[52];

    //Generate card desk of 52 cards
    public void generateCardDesk() {
        int cardPos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                if (j < 10) {
                    cards[cardPos] = new Card(i, j, (j + 1));
                }
                else {
                    cards[cardPos] = new Card(i, j, 10);
                }
                cardPos++;
            }
        }
    }

    //Draw random card from the card desk
    public Card randomDraw(){
        Random rand = new Random();
        int randomChoice;
        do {
            randomChoice = rand.nextInt(51);
        } while (cards[randomChoice] == null);
        Card randomCard = cards[randomChoice];
        cards[randomChoice] = null;
        return randomCard;
    }

    //Set all players' information: name, initial score
    public void setPlayersInfo(){
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            players[i] = new Player();
            System.out.println("Enter player #" + (i+1) + " name: ");
            String name = input.next();
            players[i].setName(name);
            players[i].cards[0] = randomDraw();
            players[i].cards[1] = randomDraw();
            int score = 0;
            for (int j = 0; j < 2; j++) {
                score += players[i].cards[j].getValue();
            }
            players[i].setScore(score);
        }
        updateMaxScore();
    }

    //Update the new Max score
    public void updateMaxScore(){
        for (int i = 0; i < 3; i++) {
            if (players[i].getScore() >  maxScore && !players[i].lost){
                maxScore = players[i].getScore();
            }
        }
    }

}
