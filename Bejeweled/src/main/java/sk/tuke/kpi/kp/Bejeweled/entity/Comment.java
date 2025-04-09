package sk.tuke.kpi.kp.Bejeweled.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Comment.getComment",
                query = "SELECT c FROM Comment c WHERE c.game=:game ORDER BY c.comment DESC"),
        @NamedQuery(name = "Comment.resetComment",
                query = "DELETE FROM Comment ")
})
public class Comment {
    @Id
    @GeneratedValue
    private int ident;
    private String game;
    private String player;
    private String comment;
    private Date commentedOn;

    public Comment(){

    }
    public Comment(String game, String player, String comment, Date commentedOn) {
        this.game = game;
        this.player = player;
        this.comment = comment;
        this.commentedOn = commentedOn;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", comment='" + comment + '\'' +
                ", commentedOn=" + commentedOn +
                '}';
    }

    public int getIdent(){
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}
