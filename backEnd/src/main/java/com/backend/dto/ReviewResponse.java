package com.backend.dto;


import com.backend.model.Content;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewResponse {
    private int id;
    private Date date ;
    private Time time ;
    private String reviewContent ;
    private boolean positive ;

    private UserResponse user ;
    private Content content;
}
