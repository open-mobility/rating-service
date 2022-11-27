package com.o4.open.ratings;

import com.o4.open.ratings.dtos.Rating;

import java.util.List;

public interface DataHelper {
    static Rating createRating(int score) {
        return createRating(score, "Junit", "UNIT");
    }

    static Rating createRating(int score, String subjectId, String subjectType) {
        Rating request = new Rating();
        request.setScore(score);
        request.setEvent("LIKE");
        request.setEventId("80-420");
        request.setSubjectType(subjectType);
        request.setSubjectId(subjectId);
        request.setTags(List.of("SMELLY", "EGOR"));
        request.setComments("I don't like it much");

        return request;
    }



}
