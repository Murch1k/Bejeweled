package sk.tuke.kpi.kp.Bejeweled.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.Bejeweled.entity.Rating;

@Component
public class RatingServiceRestClient implements RatingService{
    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) { restTemplate.postForEntity(url, rating, Void.class);
    }

    @Override
    public int getAverageRating(String game) {
        ResponseEntity<Integer> response = restTemplate.getForEntity(url + "/average/" + game, Integer.class);
        return response.getBody();
    }

    @Override
    public int getRating(String game, String player) {
        ResponseEntity<Integer> response = restTemplate.getForEntity(url + "/" + game + "/" + player, Integer.class);
        return response.getBody();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
