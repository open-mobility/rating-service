package com.o4.open.ratings.apis;

import com.o4.open.ratings.dtos.Rating;
import com.o4.open.ratings.dtos.Score;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * This is an interface/contract for our REST APIs
 * Rating API provides features like, rate any situation, any subject at anytime.
 * Its a scalable solution
 *
 * Actual implementation of the APIs will abide by this contact
 * Feign clients can use them to initialize the API clients
 * A unique code is needed for each unique error situation
 *
 * @author M. Mazhar Hassan
 * @version 1.0
 * @since 25.08.2022
 *
 */
@RequestMapping("/api/v1/ratings")
public interface RatingApi {

    /**
     * Rate a subject against any particular event
     *
     * Let's say in a movie session we want to rate the movie experience
     *
     * event: (MOVIE_SESSION) Name of the event against which rating is being done
     * eventId : (Session id) It could be any unique id with the combination of event name
     * subjectId: (movie id) ID of the item being rated
     * subjectType: (MOVIE) The item is a movie
     * actorId: ID of the person performing this action
     * score: rating for experience (1 to 5)
     *
     *
     * @param rating
     * @return Updated Rating
     */
    @PostMapping
    Rating rate(@Validated @RequestBody Rating rating);

    /**
     * Update existing rating
     * @param rating
     * @return Updated Rating
     */
    @PutMapping
    Rating update(@Validated @RequestBody Rating rating);

    /**
     * Get rating for a particular subject (CAR/MOVIE/DRIVER/ACTOR) etc
     * @param subjectId An id of the subject UUID is also supported
     * @param subjectType Type of the above ID
     * @return List of ratings
     */
    @GetMapping
    List<Rating> getRatings(@RequestParam("subjectId") String subjectId, @RequestParam("subjectType") String subjectType);

    /**
     * Get accumulative score of a subject
     * @param subjectId An id of the subject UUID is also supported
     * @param subjectType Type of the above ID
     * @return Score - The accumulative score
     */
    @GetMapping("/score")
    Score getScore(@RequestParam("subjectId") String subjectId, @RequestParam("subjectType") String subjectType);
}
