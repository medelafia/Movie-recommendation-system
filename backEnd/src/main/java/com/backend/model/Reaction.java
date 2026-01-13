package com.backend.model;

import com.backend.utils.ReactionType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private Date date ;
    private Time time ;
    private ReactionType reactionType ;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content ;

}
