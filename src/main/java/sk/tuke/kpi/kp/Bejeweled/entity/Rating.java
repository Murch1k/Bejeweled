package sk.tuke.kpi.kp.Bejeweled.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery(name = "Rating.getAverageRating",
        query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game")
@NamedQuery(name = "Rating.getRating",
        query = "SELECT r.rating FROM Rating r WHERE r.game=:game AND r.player = :player")
@NamedQuery(name = "Rating.resetRating",
        query = "DELETE FROM Rating ")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_seq")
    @SequenceGenerator(name = "rating_seq", sequenceName = "rating_ident_seq", allocationSize = 1)
    private int ident;
    private String game;
    private String player;
    private int rating;
    private Date ratedOn;

    public Rating(String game, String player, int rating, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }
    public Rating(){

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + ratedOn +
                '}';
    }

    public int getIdent(){
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}