package com.o4.open.ratings.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Score {
    private String subjectId;
    private String subjectType;
    private Double score;
    private Long sum;
    private Integer count;
}
