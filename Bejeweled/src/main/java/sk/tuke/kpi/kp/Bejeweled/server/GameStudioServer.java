package sk.tuke.kpi.kp.Bejeweled.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.kpi.kp.Bejeweled.service.*;

@SpringBootApplication(scanBasePackages = "sk.tuke.kpi.kp.Bejeweled")
@Configuration
@EntityScan("sk.tuke.kpi.kp.Bejeweled.entity")
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public RatingService ratingService(){
        return new RatingServiceJPA();
    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceJPA();
    }

    @Bean
    public ScoreTimeService scoreTimeService(){
        return new ScoreTimeServiceJPA(0);
    }
}
