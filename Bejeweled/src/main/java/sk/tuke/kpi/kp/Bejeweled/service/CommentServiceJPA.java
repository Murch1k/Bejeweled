package sk.tuke.kpi.kp.Bejeweled.service;

import sk.tuke.kpi.kp.Bejeweled.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CommentServiceJPA implements CommentService{
    @PersistenceContext
    private EntityManager entityManager;

    public void addComment(Comment comment) throws CommentException{
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return entityManager
                .createNamedQuery("Comment.getComment", Comment.class)
                .setParameter("game", game)
                .getResultList();
    }

    @Override
    public void reset()  {
        entityManager.createNamedQuery("Comment.resetComment").executeUpdate();
    }
}
