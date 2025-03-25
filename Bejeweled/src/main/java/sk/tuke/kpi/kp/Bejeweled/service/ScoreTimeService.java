package sk.tuke.kpi.kp.Bejeweled.service;

import sk.tuke.kpi.kp.Bejeweled.entity.Score;

import java.util.List;

public interface ScoreTimeService {
    void addScore(Score score) throws ScoreTimeException;
    List<Score> getTopScores(String game) throws ScoreTimeException;
    void reset() throws ScoreTimeException;
}
