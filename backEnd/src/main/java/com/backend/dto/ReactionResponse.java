package com.backend.dto;


import com.backend.model.Content;
import com.backend.utils.ReactionType;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReactionResponse {
    long id;
    private Date date ;
    private Time time ;
    private ReactionType reactionType ;


    private UserResponse user ;

    private Content content ;
}
