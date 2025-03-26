package sk.tuke.kpi.kp.Bejeweled.Game;

public class Gem {
    private String type;
    private String color;

    public Gem(String type, String color){
        this.type = type;
        this.color = color;
    }

    public boolean isMatch(Gem other){
        return this.color.equals(other.color);
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }


}
