package sk.tuke.kpi.kp.Bejeweled.service;

import sk.tuke.kpi.kp.Bejeweled.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.persist(rating);
    }


    @Override
    public int getAverageRating(String game) {
        Double avg = (Double) entityManager.createNamedQuery("Rating.getAverageRating")
                .setParameter("game", game)
                .getSingleResult();
        return avg != null ? avg.intValue() : 0;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            Rating rating = entityManager
                    .createNamedQuery("Rating.getRating", Rating.class)
                    .setParameter("game", game)
                    .setParameter("player", player)
                    .getSingleResult();

            return rating != null ? rating.getRating() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNamedQuery("Rating.resetRating").executeUpdate();
    }
}
