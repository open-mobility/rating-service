package com.o4.open.ratings.controllers;

import com.o4.open.ratings.apis.RatingApi;
import com.o4.open.ratings.dtos.Rating;
import com.o4.open.ratings.dtos.Score;
import com.o4.open.ratings.service.RatingService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implementation of the Rating contract
 *
 * @see com.o4.open.ratings.apis.RatingApi
 */
@RestController
public class RatingController implements RatingApi {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    /**
     * @see com.o4.open.ratings.apis.RatingApi#rate(Rating)
     */
    @Override
    public Rating rate(Rating rating) {

        return service.rate(rating);
    }

    /**
     * @see com.o4.open.ratings.apis.RatingApi#update(Rating) 
     */
    @Override
    public Rating update(Rating rating) {

        return service.update(rating);
    }

    /**
     * @see com.o4.open.ratings.apis.RatingApi#getRatings(String, String) 
     */
    @Override
    public List<Rating> getRatings(String subjectId, String subjectType) {

        return service.getRatings(subjectId, subjectType);
    }

    /**
     * @see com.o4.open.ratings.apis.RatingApi#getScore(String, String)  
     */
    @Override
    public Score getScore(String subjectId, String subjectType) {

        return service.getScore(subjectId, subjectType);
    }
}
