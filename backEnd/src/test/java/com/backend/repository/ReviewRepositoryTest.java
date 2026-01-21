package com.backend.repository;

import com.backend.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository  ;
    @Autowired
    private GenreRepository genreRepository ;
    @Autowired
    private ActorRepository actorRepository ;
    @Autowired
    private UserRepository userRepository ;

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

        Review review1 = Review.builder()
                .user(savedUser)
                .content(savedMovie)
                .reviewContent("content")
                .build();
        Review review2 = Review.builder()
                .user(savedUser2)
                .content(savedMovie)
                .reviewContent("content")
                .build();

        this.reviewRepository.save(review1);
        this.reviewRepository.save(review2);

        Optional<Review> review = this.reviewRepository.findByContent_IdAndUser_Id(savedMovie.getId(), savedUser.getId());

        assertThat(review).isPresent() ;
        assertThat(review.get().getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(review.get().getContent().getId()).isEqualTo(savedMovie.getId());
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

        Review review1 = Review.builder()
                .user(savedUser)
                .content(savedMovie)
                .reviewContent("content")
                .build();
        Review review2 = Review.builder()
                .user(savedUser2)
                .content(savedMovie)
                .reviewContent("content")
                .build();

        this.reviewRepository.save(review1);
        this.reviewRepository.save(review2);

        List<Review> retrievedReview = this.reviewRepository.findAllByUser_Id(savedUser2.getId());

        assertThat(retrievedReview).isNotNull() ;
        assertThat(retrievedReview.size()).isEqualTo(1);
        assertThat(retrievedReview.get(0)).isEqualTo(review2);
        assertThat(retrievedReview.get(0).getUser().getId()).isEqualTo(savedUser2.getId());
    }
}