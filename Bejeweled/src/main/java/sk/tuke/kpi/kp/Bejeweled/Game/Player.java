package sk.tuke.kpi.kp.Bejeweled.Game;

public class Player {
    private int score;

    public Player(){
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }
}
