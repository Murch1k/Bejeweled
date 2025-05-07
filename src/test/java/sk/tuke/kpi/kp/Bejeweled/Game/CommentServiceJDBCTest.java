package sk.tuke.kpi.kp.Bejeweled.Game;

import org.junit.jupiter.api.*;
import sk.tuke.kpi.kp.Bejeweled.entity.Comment;
import sk.tuke.kpi.kp.Bejeweled.service.CommentServiceJDBC;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceJDBCTest {

    private CommentServiceJDBC service;

    @BeforeEach
    public void setUp() {
        service = new CommentServiceJDBC();
        service.reset();
    }

    @Test
    public void testAddAndGetComment() {
        Comment comment = new Comment("Bejeweled", "Alice", "Nice game!", new Date());
        service.addComment(comment);

        List<Comment> comments = service.getComments("Bejeweled");
        assertEquals(1, comments.size());
        assertEquals("Nice game!", comments.get(0).getComment());
    }

    @Test
    public void testReset() {
        service.addComment(new Comment("Bejeweled", "Bob", "Cool!", new Date()));
        service.reset();

        assertTrue(service.getComments("Bejeweled").isEmpty());
    }
}
