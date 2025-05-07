package sk.tuke.kpi.kp.Bejeweled.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.Bejeweled.entity.ScoreTime;

import java.util.Arrays;
import java.util.List;

@Component
public class ScoreTimeServiceRestClient implements ScoreTimeService{
    private final String url = "http://localhost:8080/api/scoreTime";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addScore(ScoreTime scoreTime) {
        restTemplate.postForEntity(url, scoreTime, ScoreTime.class);
    }

    @Override
    public List<ScoreTime> getTopScoresByTime(String gameName) {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + gameName, ScoreTime[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
