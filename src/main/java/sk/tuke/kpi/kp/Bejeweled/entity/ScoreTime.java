package sk.tuke.kpi.kp.Bejeweled.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "ScoreTime.getTopScoresByTimeLimit",
                query = "SELECT s FROM ScoreTime s WHERE s.game = :game AND s.timeLimit = :time ORDER BY s.points DESC"),
        @NamedQuery(name = "ScoreTime.resetScores",
                query = "DELETE FROM ScoreTime")
})

public class ScoreTime {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_time_seq")
    @SequenceGenerator(name = "score_time_seq", sequenceName = "score_time_ident_seq", allocationSize = 1)
    private int ident;
    private String game;
    private String player;
    private int points;
    private Date playedOn;
    private int timeLimit;

    public ScoreTime(String game, String player, int points, int timeLimit, Date playedOn) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.timeLimit = timeLimit;
        this.playedOn = playedOn;
    }
    public ScoreTime(){

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

    public int getTimeLimit() { return timeLimit; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }

    @Override
    public String toString() {
        return "ScoreTime{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", time=" + timeLimit +
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
