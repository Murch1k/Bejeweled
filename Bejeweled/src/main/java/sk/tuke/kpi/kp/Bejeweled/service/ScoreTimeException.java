package sk.tuke.kpi.kp.Bejeweled.service;

public class ScoreTimeException extends RuntimeException {
    public ScoreTimeException(String message) {
        super(message);
    }
    public ScoreTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
