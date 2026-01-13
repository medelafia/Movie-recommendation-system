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
public class RatingResponse {
    long id;
    private Date date ;
    private Time time ;
    private float givingRate ;

    private UserResponse user ;

    private Content content ;
}
