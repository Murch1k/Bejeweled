package sk.tuke.kpi.kp.Bejeweled.service;

import sk.tuke.kpi.kp.Bejeweled.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "10081008Alex";

    private static final String INSERT_COMMENT =
            "INSERT INTO comment (player, game, comment, commentedOn) VALUES (?, ?, ?, ?)";
    private static final String SELECT_COMMENTS =
            "SELECT player, game, comment, commentedOn FROM comment WHERE game = ? ORDER BY commentedOn DESC";
    private static final String DELETE_COMMENTS =
            "DELETE FROM comment";

    @Override
    public void addComment(Comment comment) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(INSERT_COMMENT)) {

            ps.setString(1, comment.getPlayer());
            ps.setString(2, comment.getGame());
            ps.setString(3, comment.getComment());
            ps.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Error inserting comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(SELECT_COMMENTS)) {

            ps.setString(1, game);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment c = new Comment(
                            rs.getString("game"),
                            rs.getString("player"),
                            rs.getString("comment"),
                            rs.getTimestamp("commentedOn")
                    );
                    comments.add(c);
                }
            }
        } catch (SQLException e) {
            throw new CommentException("Error selecting comments", e);
        }
        return comments;
    }

    @Override
    public void reset() throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = connection.createStatement()) {

            st.executeUpdate(DELETE_COMMENTS);
        } catch (SQLException e) {
            throw new CommentException("Error deleting comments", e);
        }
    }
}