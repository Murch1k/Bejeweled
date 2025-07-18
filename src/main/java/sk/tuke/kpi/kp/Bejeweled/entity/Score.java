package sk.tuke.kpi.kp.Bejeweled.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery(name = "Score.getTopScores",
        query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.points DESC")
@NamedQuery(name = "Score.resetScores",
        query = "DELETE FROM Score")

public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_seq")
    @SequenceGenerator(name = "score_seq", sequenceName = "score_ident_seq", allocationSize = 1)
    private int ident;
    private String game;
    private String player;
    private int points;
    private Date playedOn;

    public Score(String game, String player, int points, Date playedOn) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
    }
    public Score(){

    }
    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedOn=" + playedOn +
                '}';
    }

    public int getIdent(){
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}
