package sk.tuke.kpi.kp.Bejeweled.Game;

public class Player {
    private String name;
    private int score;

    public Player(String name){
        this.name = name;
        this.score = 0;
    }

    public String getName(){
        return name;
    }
    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }
}
