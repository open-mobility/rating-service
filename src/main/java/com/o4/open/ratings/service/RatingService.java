package com.o4.open.ratings.service;

import com.o4.open.ratings.dtos.Rating;
import com.o4.open.ratings.dtos.Score;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RatingService {

    Rating rate(@Validated @RequestBody Rating rating);

    Rating update(@Validated @RequestBody Rating rating);

    List<Rating> getRatings(@RequestParam("subjectId") String subjectId, @RequestParam("subjectType") String subjectType);

    Score getScore(@RequestParam("subjectId") String subjectId, @RequestParam("subjectType") String subjectType);
}
