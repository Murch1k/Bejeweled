package sk.tuke.kpi.kp.Bejeweled.Test;

public class Gem {
    private String type;
    private String color;
    private boolean isPowerGem;
    private String effect;
    public Gem(String type, String color, boolean isPowerGem, String effect){
        this.type = type;
        this.color = color;
        this.isPowerGem = isPowerGem;
        this.effect = effect;
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

    public boolean isPowerGem() {
        return isPowerGem;
    }

    public String getEffect() {
        return effect;
    }
}
