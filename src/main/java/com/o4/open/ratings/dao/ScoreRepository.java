package com.o4.open.ratings.dao;

import com.o4.open.ratings.dtos.Score;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static com.o4.open.ratings.dao.KeyHelper.createKey;

@Repository
public class ScoreRepository {
    private static Map<String, Score> scores = new HashMap<>();
    public boolean exists(String key) {
        return scores.containsKey(key);
    }

    public Score findByKey(String key) {
        return scores.get(key);
    }

    public void save(Score score) {
        scores.put(createKey(score.getSubjectId(), score.getSubjectType()), score);
    }
}
