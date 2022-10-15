package blackjack;

import java.util.Scanner;

public class BlackJack {
    static Game game = new Game();
    static GUI gui = new GUI();

    public static void main(String[] args) {

        game.generateCardDesk();
        game.setPlayersInfo();
        gui.runGUI(game.cards, game.players[0].cards, game.players[1].cards, game.players[2].cards, game.players[3].cards);
        playersTurn();
        dealerTurn();
        gameResult();
    }

    public static void playersTurn() {
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            int score = game.players[i].getScore();
            System.out.println("Player #" + (i + 1) + " " + game.players[i].getName() + " turn");
            System.out.println("For HIT press 1 & for STAND press 2: ");
            int key = input.nextInt();
            int cardNum = 2;
            while (key == 1) {
                game.players[i].cards[cardNum] = game.randomDraw();
                gui.updatePlayerHand(game.players[i].cards[cardNum], i);
                score += game.players[i].cards[cardNum].getValue();
                game.players[i].setScore(score);
                if (score > 21) {
                    game.players[i].lost = true;
                    System.out.println("Player #" + (i + 1) + " " + game.players[i].getName() + " lost (BUSTED)!!");
                    break;
                }
                game.updateMaxScore();
                cardNum++;

                System.out.println("For HIT press 1 & for STAND press 2: ");
                key = input.nextInt();
            }
            if (score == 21) {
                game.players[i].blackJack = true;
            }
        }
    }

    public static void dealerTurn() {
        int score = game.players[3].getScore();
        System.out.println("Player #4"+ " " + game.players[3].getName() + " turn");
        if (score > Game.maxScore) { //Check if the dealer has a score grater than the max score
            Game.maxScore = score;
        } else {
            int cardNum = 2;
            while (true) {
                game.players[3].cards[cardNum] = game.randomDraw();
                gui.updateDealerHand(game.players[3].cards[cardNum], game.cards);
                score += game.players[3].cards[cardNum].getValue();
                game.players[3].setScore(score);
                cardNum++;

                if (score == 21) {
                    game.players[3].blackJack = true;
                    break;
                } else if (score > 21) {
                    game.players[3].lost = true;
                    System.out.println("Player #4" + " " + game.players[3].getName() + " lost (BUSTED)!!");
                    break;
                } else if (score > Game.maxScore) {
                    Game.maxScore = score;
                    break;
                }
            }
        }
    }

    //Show the end result of the game a certain player wins, or it's tie
    public static void gameResult() {
        int bjPlayersIndex = -1;
        int bjPlayersNum = 0; //Check number of players have BlackJack
        for (int i = 0; i < 4; i++) {
            if (game.players[i].blackJack) {
                bjPlayersNum++;
                bjPlayersIndex = i;
            }
            if (bjPlayersNum > 1) {
                System.out.println("Tie (PUSH)!! [Two players or more have blackjack]");
                break;
            }
        }
        if (bjPlayersNum == 1) {
            System.out.println("Player #" + (bjPlayersIndex + 1) + " " + game.players[bjPlayersIndex].getName() + " Won!!! [BlackJack]");
        }

        if (bjPlayersNum == 0) {

            int highestScorePlayerIndex = -1;
            int highScorePlayers = 0; //Check number of players have score equal to the current max score
            for (int i = 0; i < 4; i++) {
                if (game.players[i].getScore() == Game.maxScore) {
                    highestScorePlayerIndex = i;
                    highScorePlayers++;
                }

                if (highScorePlayers > 1) {
                    System.out.println("Tie (PUSH)!! [Two players or more have the highestscore]");
                    break;
                }
            }
            if (highScorePlayers == 1) {
                System.out.println("Player #" + (highestScorePlayerIndex + 1) + " " + game.players[highestScorePlayerIndex].getName() + " Won!!! [HighestScore]");
            }
        }
    }
}

