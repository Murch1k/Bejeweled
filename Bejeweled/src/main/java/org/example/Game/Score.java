package org.example.Game;

public class Score {

    public int calculateScore(int sizeGems){
        int baseScore = sizeGems * 10;
        if (sizeGems >= 5) {
            baseScore += 50;
        } else if (sizeGems >= 4) {
            baseScore += 20;
        }
        return baseScore;
    }
}
