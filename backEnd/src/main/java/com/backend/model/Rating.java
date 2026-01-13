package com.backend.model;

import com.backend.utils.ReactionType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private Date date ;
    private Time time ;
    private float givingRate ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content ;
}
