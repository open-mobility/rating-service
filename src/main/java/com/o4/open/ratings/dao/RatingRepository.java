package com.o4.open.ratings.dao;

import com.o4.open.ratings.dtos.Rating;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.o4.open.ratings.dao.KeyHelper.createKey;

@Repository
public class RatingRepository {
    private static long id = 0;
    private static Map<String, List<Rating>> db = new HashMap<>();

    public Rating save(Rating rating) {

        String key = createKey(rating);
        db.putIfAbsent(key, new ArrayList<>());

        rating.setRateId(++id);
        db.get(key).add(rating);

        return rating;
    }


    public boolean exists(String key) {
        return db.containsKey(key);
    }

    public List<Rating> findAllByKey(String key) {
        return db.get(key);
    }

}
