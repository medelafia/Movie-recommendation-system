package com.backend.utils;

import com.backend.model.*;

import java.util.List;
import java.util.Set;

public class TestingUtils {

    public static List<Content> getContentsToTest() {
        Content content = Content.builder().title("Breaking bad")
                .overview("overview")
                .genres(Set.of(new Genre("genre")))
                .actors(Set.of(new Actor("actor")))
                .build();
        Content content2 = Content.builder().title("Vikings")
                .overview("overview")
                .genres(Set.of(new Genre("genre")))
                .actors(Set.of(new Actor("actor")))
                .build();
        Content content3 = Content.builder()
                .title("John Wick")
                .overview("overview")
                .genres(Set.of(new Genre("genre")))
                .actors(Set.of(new Actor("actor")))
                .build();

        return List.of(content, content2, content3);
    }
    public static List<Movie>  getMoviesToTest() {
        Movie movie1 = Movie.builder()
                .title("John Wick")
                .overview("overview")
                .externalRating(7.5f)
                .voteCount(10)
                .releasedYear(2020)
                .runtime(120)
                .certification("Certification")
                .director("Director")
                .posterLink("posterLink")
                .genres(Set.of(new Genre("genre")))
                .actors(Set.of(new Actor("actor")))
                .build() ;
        Movie movie2 = Movie.builder()
                .title("8 Mile")
                .overview("overview")
                .externalRating(7.5f)
                .voteCount(10)
                .releasedYear(2020)
                .runtime(120)
                .certification("Certification")
                .director("Director")
                .posterLink("posterLink")
                .genres(Set.of(new Genre("genre")))
                .actors(Set.of(new Actor("actor")))
                .build() ;
        return List.of(movie1, movie2);
    }
    public static List<Series> getSeriesToTest() {
        Series series1 = Series.builder()
                .title("Breaking bad")
                .overview("overview")

                .genres(Set.of(new Genre("genre")))
                .actors(Set.of(new Actor("actor")))
                .build() ;

        Series series2 = Series.builder()
                .title("Vikings")
                .overview("overview")
                .genres(Set.of(new Genre("genre")))
                .actors(Set.of(new Actor("actor")))
                .build() ;

        return List.of(series1, series2);
    }
}
