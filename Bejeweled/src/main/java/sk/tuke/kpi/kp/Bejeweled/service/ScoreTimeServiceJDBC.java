package sk.tuke.kpi.kp.Bejeweled.service;

import sk.tuke.kpi.kp.Bejeweled.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreTimeServiceJDBC implements ScoreTimeService {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "10081008Alex";

    private static final String INSERT =
            "INSERT INTO score_time (game, player, points, playedOn, timeLimit) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT =
            "SELECT game, player, points, playedOn FROM score_time WHERE game = ? AND timeLimit = ? ORDER BY points DESC LIMIT 10";
    private static final String DELETE = "DELETE FROM score_time";

    private int currentTimeLimit;

    public ScoreTimeServiceJDBC(int timeLimit) {
        this.currentTimeLimit = timeLimit;
    }

    @Override
    public void addScore(Score score) throws ScoreTimeException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, score.getGame());
            ps.setString(2, score.getPlayer());
            ps.setInt(3, score.getPoints());
            ps.setTimestamp(4, new Timestamp(score.getPlayedOn().getTime()));
            ps.setInt(5, currentTimeLimit);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreTimeException("Error inserting timed score", e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) throws ScoreTimeException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(SELECT)) {
            ps.setString(1, game);
            ps.setInt(2, currentTimeLimit);
            try (ResultSet rs = ps.executeQuery()) {
                List<Score> scores = new ArrayList<>();
                while (rs.next()) {
                    scores.add(new Score(
                            rs.getString("game"),
                            rs.getString("player"),
                            rs.getInt("points"),
                            rs.getTimestamp("playedOn")
                    ));
                }
                return scores;
            }
        } catch (SQLException e) {
            throw new ScoreTimeException("Error selecting timed scores", e);
        }
    }

    @Override
    public void reset() throws ScoreTimeException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = connection.createStatement()) {
            st.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new ScoreTimeException("Error deleting timed scores", e);
        }
    }
}
