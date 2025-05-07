package sk.tuke.kpi.kp.Bejeweled.service;

import sk.tuke.kpi.kp.Bejeweled.entity.Rating;

import java.sql.*;

public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "10081008Alex";


    private static final String SELECT_RATING =
            "SELECT rating FROM rating WHERE game = ? AND player = ?";
    private static final String INSERT_RATING =
            "INSERT INTO rating (player, game, rating, ratedOn) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_RATING =
            "UPDATE rating SET rating = ?, ratedOn = ? WHERE game = ? AND player = ?";
    private static final String SELECT_AVG_RATING =
            "SELECT AVG(rating) FROM rating WHERE game = ?";
    private static final String DELETE_RATINGS =
            "DELETE FROM rating";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement checkStmt = connection.prepareStatement(SELECT_RATING)) {
                checkStmt.setString(1, rating.getGame());
                checkStmt.setString(2, rating.getPlayer());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        try (PreparedStatement updateStmt = connection.prepareStatement(UPDATE_RATING)) {
                            updateStmt.setInt(1, rating.getRating());
                            updateStmt.setTimestamp(2, new Timestamp(rating.getRatedOn().getTime()));
                            updateStmt.setString(3, rating.getGame());
                            updateStmt.setString(4, rating.getPlayer());
                            updateStmt.executeUpdate();
                        }
                    } else {
                        try (PreparedStatement insertStmt = connection.prepareStatement(INSERT_RATING)) {
                            insertStmt.setString(1, rating.getPlayer());
                            insertStmt.setString(2, rating.getGame());
                            insertStmt.setInt(3, rating.getRating());
                            insertStmt.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Error setting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(SELECT_AVG_RATING)) {

            ps.setString(1, game);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double avg = rs.getDouble(1);
                    return (int) Math.round(avg);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new RatingException("Error getting average rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(SELECT_RATING)) {

            ps.setString(1, game);
            ps.setString(2, player);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new RatingException("Error getting rating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = connection.createStatement()) {

            st.executeUpdate(DELETE_RATINGS);
        } catch (SQLException e) {
            throw new RatingException("Error deleting ratings", e);
        }
    }
}
