package com.backend.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE"  , discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String title ;
    @Lob
    private String overview ;
    private String posterLink ;
    private Float externalRating;
    private Integer voteCount ;
    private Integer releasedYear ;
    private String certification ;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "content_genres",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_name")
    )
    private Set<Actor> actors ;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "content_actors",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_name")
    )
    private Set<Genre> genres ;
}
