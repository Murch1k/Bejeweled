package sk.tuke.kpi.kp.Bejeweled.service;

import sk.tuke.kpi.kp.Bejeweled.entity.Score;
import sk.tuke.kpi.kp.Bejeweled.entity.ScoreTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Transactional
public class ScoreTimeServiceJPA implements ScoreTimeService {

    @PersistenceContext
    private EntityManager entityManager;

    private int timeLimit;
    public ScoreTimeServiceJPA(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Override
    public void addScore(ScoreTime score) throws ScoreTimeException {
        score.setTimeLimit(timeLimit);
        entityManager.persist(score);
    }

    @Override
    public List<ScoreTime> getTopScoresByTime(String game) throws ScoreTimeException {
        return entityManager
                .createNamedQuery("ScoreTime.getTopScoresByTimeLimit", ScoreTime.class)
                .setParameter("game", game)
                .setParameter("time", timeLimit)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("ScoreTime.resetScores").executeUpdate();
    }
}
