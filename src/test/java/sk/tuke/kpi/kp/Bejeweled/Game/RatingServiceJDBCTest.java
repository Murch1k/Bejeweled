package sk.tuke.kpi.kp.Bejeweled.Game;

import org.junit.jupiter.api.*;
import sk.tuke.kpi.kp.Bejeweled.entity.Rating;
import sk.tuke.kpi.kp.Bejeweled.service.RatingServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RatingServiceJDBCTest {

    private RatingServiceJDBC service;

    @BeforeEach
    public void setUp() {
        service = new RatingServiceJDBC();
        service.reset();
    }

    @Test
    public void testSetAndGetRating() {
        Rating rating = new Rating("Bejeweled", "Alice", 4, new Date());
        service.setRating(rating);

        assertEquals(4, service.getRating("Bejeweled", "Alice"));
    }

    @Test
    public void testAverageRating() {
        service.setRating(new Rating("Bejeweled", "A", 4, new Date()));
        service.setRating(new Rating("Bejeweled", "B", 5, new Date()));

        assertEquals(5, service.getRating("Bejeweled", "B"));
        assertEquals(5, service.getRating("Bejeweled", "B"));
        assertEquals(5, service.getAverageRating("Bejeweled")); // округление
    }

    @Test
    public void testReset() {
        service.setRating(new Rating("Bejeweled", "A", 3, new Date()));
        service.reset();
        assertEquals(0, service.getRating("Bejeweled", "A"));
    }
}
