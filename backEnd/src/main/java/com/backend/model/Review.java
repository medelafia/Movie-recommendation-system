package com.backend.model;

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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date ;
    private Time time ;
    private String reviewContent ;
    private boolean positive ;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content ;
}
