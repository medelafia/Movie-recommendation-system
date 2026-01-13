package com.backend.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@DiscriminatorValue("MOVIE")
public class Movie extends Content {
    private float runtime ;
    private String director ;
}
