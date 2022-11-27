package com.o4.open.ratings.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Rating {
    private Long rateId;
    private String event;
    private String eventId;
    private String subjectId;
    private String subjectType;
    private Long actorId;
    private Integer score;
    private String comments;
    private List<String> tags;
}
