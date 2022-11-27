package com.o4.open.ratings.dao;

import com.o4.open.ratings.dtos.Rating;

public interface KeyHelper {
    static String createKey(String id, String type) {
        return id+"_"+type;
    }

    static String createKey(Rating rate) {
        return createKey(rate.getSubjectId(), rate.getSubjectType());
    }
}
