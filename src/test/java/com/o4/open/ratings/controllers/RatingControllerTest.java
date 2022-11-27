package com.o4.open.ratings.controllers;

import com.o4.open.commons.utils.JsonUtils;
import com.o4.open.ratings.DataHelper;
import com.o4.open.ratings.dao.RatingRepository;
import com.o4.open.ratings.dao.ScoreRepository;
import com.o4.open.ratings.dtos.Rating;
import com.o4.open.ratings.dtos.Score;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static com.o4.open.ratings.dao.KeyHelper.createKey;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RatingControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    ScoreRepository scoreRepository;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    void assertRating(Rating expected, Rating actual) {
        assertEquals(expected.getActorId(), actual.getActorId());
        assertEquals(expected.getEvent(), actual.getEvent());
        assertEquals(expected.getEventId(), actual.getEventId());
        assertEquals(expected.getScore(), actual.getScore());

        assertEquals(expected.getSubjectId(), actual.getSubjectId());
        assertEquals(expected.getSubjectType(), actual.getSubjectType());

        assertEquals(expected.getComments(), actual.getComments());
        assertEquals(expected.getTags(), actual.getTags());
    }

    @Test
    void rate() throws Exception {

        Integer score = 4;
        Rating request = DataHelper.createRating(score);
        request.setEvent("JUNIT_RATE_001");
        request.setEventId("2022-11-26-07-20");

        //make the call and verify API response
        Rating response = createRating(request);

        //Database saved verification
        List<Rating> savedRatings = ratingRepository.findAllByKey(createKey(request));
        assertNotNull(savedRatings, "Nothing was saved");
        assertTrue(savedRatings.size() > 0);

        Optional<Rating> filter = savedRatings.stream()
                .filter(r -> request.getEventId().equals(r.getEventId()) && request.getEvent().equals(r.getEvent()))
                .findFirst();
        assertTrue(filter.isPresent());

        Rating found = filter.get();
        assertRating(request, found);

    }

    @Test
    void update() throws Exception {

        //1 - Arrange
        Integer score = 3;
        Rating request = DataHelper.createRating(score);
        request.setEvent("JUNIT_UPDATE_001");
        request.setEventId("2022-11-26-07-22");

        //Create new rating
        Rating response = createRating(request);

        //2 - Action, modify rating
        response.setScore(4);
        response.setComments("Modified comments");
        response.setTags(List.of("ALL", "Modified", "Tags"));

        Rating modified = updateRating("/api/v1/ratings", response, status().isOk());

        //3 - Assert
        assertRating(response, modified);
    }

    @Test
    void getRatings() throws Exception {
        Rating r1 = DataHelper.createRating(4, "SNIPER-ELITE", "GAME");
        Rating r2 = DataHelper.createRating(2,"SNIPER-ELITE", "GAME");

        createRating(r1);
        createRating(r2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ratings")
                        .param("subjectId", r1.getSubjectId())
                        .param("subjectType", r1.getSubjectType()))
                .andExpect(status().isOk())
                .andReturn();
        List<Rating> ratings = JsonUtils.toObjectList(result.getResponse().getContentAsString(), Rating.class);
        assertRating(r1, ratings.get(0));
        assertRating(r2, ratings.get(1));

    }

    @Test
    void getScore() throws Exception {

        Rating r1 = DataHelper.createRating(4, "ROBLOX", "GAME");
        Rating r2 = DataHelper.createRating(2,"ROBLOX", "GAME");

        createRating(r1);
        createRating(r2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ratings/score")
                        .param("subjectId", r1.getSubjectId())
                        .param("subjectType", r1.getSubjectType()))
                .andExpect(status().isOk())
                .andReturn();
        Score response = JsonUtils.toObject(result.getResponse().getContentAsString(), Score.class);
        assertEquals(2, response.getCount());
        assertEquals(6, response.getSum());
        assertEquals(3D, response.getScore());
    }

    private MvcResult createRating(Rating requestBody, ResultMatcher statusMatcher) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(requestBody)))
                .andExpect(statusMatcher)
                .andReturn();
    }

    private Rating updateRating(String url, Rating requestBody, ResultMatcher statusMatcher) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(requestBody)))
                .andExpect(statusMatcher)
                .andReturn();

        return JsonUtils.toObject(result.getResponse().getContentAsString(), Rating.class);
    }

    private Rating createRating(Rating request) throws Exception {

        //setup
        MvcResult result = createRating(request, status().isOk());
        Rating response = JsonUtils.toObject(result.getResponse().getContentAsString(), Rating.class);

        //verify API response
        assertTrue(response.getRateId() > 0, "An auto increment Id should be assigned");
        assertRating(request, response);

        return response;
    }

}