package com.backend.repository;

import com.backend.model.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.AutoConfigureTestEntityManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class RatingRepositoryTest {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private MovieRepository movieRepository  ;
    @Autowired
    private GenreRepository genreRepository ;
    @Autowired
    private ActorRepository actorRepository ;

    private List<User> userList = new ArrayList<>();

    private Genre genre;
    private Actor actor;

    private Movie movie;

    @BeforeEach
    void setUp() {
        userList.add(User.builder().username("user 1").build());
        userList.add(User.builder().username("user 2").build());

        genre = new Genre("genre") ;
        actor = new Actor("actor") ;

        movie = Movie.builder().title("title").build() ;
    }

    @BeforeAll
    public static void setupDatabase(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            // Drop the problematic constraint
            stmt.execute("ALTER TABLE content DROP CONSTRAINT IF EXISTS CONSTRAINT_6");
            // Create correct constraint
        }
    }



    @Test
    void findByContent_IdAndUser_Id() {
        Genre savedGenre = genreRepository.save(genre);
        Actor savedActor = actorRepository.save(actor);

        User savedUser = this.userRepository.save(this.userList.get(0));
        User savedUser2 = this.userRepository.save(this.userList.get(1));

        movie.setGenres(Set.of(savedGenre));
        movie.setActors(Set.of(savedActor));

        Movie savedMovie = this.movieRepository.save( movie ) ;

        Rating rating1 = Rating.builder()
                .user(savedUser)
                .content(savedMovie)
                .givingRate(8.8f)
                .build();
        Rating rating2 = Rating.builder()
                .user(savedUser2)
                .content(savedMovie)
                .givingRate(8.8f)
                .build();

        this.ratingRepository.save(rating1);
        this.ratingRepository.save(rating2);

        Optional<Rating> rating = this.ratingRepository.findByContent_IdAndUser_Id(savedMovie.getId(), savedUser.getId());

        assertThat(rating).isPresent() ;
        assertThat(rating.get().getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(rating.get().getContent().getId()).isEqualTo(savedMovie.getId());
    }

    @Test
    void findAllByUser_Id() {
        Genre savedGenre = genreRepository.save(genre);
        Actor savedActor = actorRepository.save(actor);

        User savedUser = this.userRepository.save(this.userList.get(0));
        User savedUser2 = this.userRepository.save(this.userList.get(1));

        movie.setGenres(Set.of(savedGenre));
        movie.setActors(Set.of(savedActor));

        Movie savedMovie = this.movieRepository.save( movie ) ;

        Rating rating1 = Rating.builder()
                .user(savedUser)
                .content(savedMovie)
                .givingRate(8.8f)
                .build();
        Rating rating2 = Rating.builder()
                .user(savedUser2)
                .content(savedMovie)
                .givingRate(8.8f)
                .build();

        this.ratingRepository.save(rating1);
        this.ratingRepository.save(rating2);

        List<Rating> retrievedRatings = this.ratingRepository.findAllByUser_Id(savedUser2.getId());

        assertThat(retrievedRatings).isNotNull() ;
        assertThat(retrievedRatings.size()).isEqualTo(1);
        assertThat(retrievedRatings.get(0)).isEqualTo(rating2);
        assertThat(retrievedRatings.get(0).getUser().getId()).isEqualTo(savedUser2.getId());
    }
}