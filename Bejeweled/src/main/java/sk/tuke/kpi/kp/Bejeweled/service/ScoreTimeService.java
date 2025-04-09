package sk.tuke.kpi.kp.Bejeweled.service;

import sk.tuke.kpi.kp.Bejeweled.entity.ScoreTime;

import java.util.List;

public interface ScoreTimeService {
    void addScore(ScoreTime score) throws ScoreTimeException;
    List<ScoreTime> getTopScores(String game) throws ScoreTimeException;
    void reset() throws ScoreTimeException;
}
