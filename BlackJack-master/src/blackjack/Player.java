package blackjack;

public class Player {
    private String name;
    private int score;
    Card []cards = new Card[11];
    boolean blackJack;
    boolean lost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
