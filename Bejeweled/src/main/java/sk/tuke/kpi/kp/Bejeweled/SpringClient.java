package sk.tuke.kpi.kp.Bejeweled;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.kpi.kp.Bejeweled.Game.Board;
import sk.tuke.kpi.kp.Bejeweled.Game.Player;
import sk.tuke.kpi.kp.Bejeweled.service.*;
import sk.tuke.kpi.kp.Bejeweled.ui.ConsoleUI;

@SpringBootApplication
@Configuration
public class SpringClient {
    private Player player;
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.startGame();
    }

    @Bean
    public ConsoleUI consoleUI() {
        return new ConsoleUI();
    }

    @Bean
    public Board board() {
        return new Board(8, 8, player);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public ScoreTimeService scoreTimeService(){
        return new ScoreTimeServiceJPA(0);
    }

    @Bean
    public RatingService ratingService(){
        return new RatingServiceJPA();
    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceJPA();
    }
}
