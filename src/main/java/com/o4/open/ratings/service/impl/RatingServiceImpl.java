package com.o4.open.ratings.service.impl;

import com.o4.open.commons.exceptions.ResourceNotFoundException;
import com.o4.open.ratings.dao.RatingRepository;
import com.o4.open.ratings.dao.ScoreRepository;
import com.o4.open.ratings.dtos.Rating;
import com.o4.open.ratings.dtos.Score;
import com.o4.open.ratings.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.o4.open.ratings.dao.KeyHelper.createKey;

@Service
public class RatingServiceImpl implements RatingService {

   private final RatingRepository ratingRepository;
   private final ScoreRepository scoreRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, ScoreRepository scoreRepository) {
        this.ratingRepository = ratingRepository;
        this.scoreRepository = scoreRepository;
    }

    @Override
    public Rating rate(Rating rating) {

        Rating response = ratingRepository.save(rating);
        updateScore(rating.getSubjectId(), rating.getSubjectType());

        return response;
    }

    @Override
    public Rating update(Rating rating) {
        String key = createKey(rating);

        if (!ratingRepository.exists(key)) {
            throw new ResourceNotFoundException("Rating for the provided subject not found");
        }

        Optional<Rating> found = ratingRepository.findAllByKey(key)
                .stream()
                .filter(r -> r.getEvent().equals(rating.getEvent()) && r.getEventId().equals(rating.getEventId()))
                .findFirst();

        if (found.isEmpty()) {
            throw new ResourceNotFoundException("Rating for event is not available");
        }

        Rating record = found.get();
        record.setScore(rating.getScore());
        record.setComments(rating.getComments());
        record.setTags(rating.getTags());

        // @TODO Must be done asynchronously
        updateScore(rating.getSubjectId(), rating.getSubjectType());

        return record;
    }

    @Override
    public List<Rating> getRatings(String subjectId, String subjectType) {
        String key = createKey(subjectId, subjectType);
        if (!ratingRepository.exists(key)) {
            throw new ResourceNotFoundException("Rating for the provided subject not found");
        }

        return ratingRepository.findAllByKey(key);
    }

    @Override
    public Score getScore(String subjectId, String subjectType) {
        String key = createKey(subjectId, subjectType);
        Score score;
        if (!scoreRepository.exists(key)) {
            score = new Score();
            score.setScore(0D);
            score.setSum(0L);
            score.setCount(0);
            score.setSubjectType(subjectType);
            score.setSubjectId(subjectId);
        } else {
            score = scoreRepository.findByKey(key);
        }

        return score;
    }

    private void updateScore(String subjectId, String subjectType) {
        String key = createKey(subjectId, subjectType);

        if (!ratingRepository.exists(key)) {
            throw new ResourceNotFoundException("Rating for the provided subject not found");
        }

        List<Rating> ratings = ratingRepository.findAllByKey(key);

        Score score = new Score();
        score.setSubjectId(subjectId);
        score.setSubjectType(subjectType);
        score.setSum(ratings.stream().mapToLong(rating -> rating.getScore()).sum());
        score.setCount(ratings.size());

        if (score.getCount() > 0) {
            score.setScore((double) (score.getSum() / score.getCount()));
        }

        scoreRepository.save(score);
    }

}
