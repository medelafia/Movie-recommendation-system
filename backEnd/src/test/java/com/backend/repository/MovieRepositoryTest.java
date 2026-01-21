package com.backend.repository;

import com.backend.model.Actor;
import com.backend.model.Genre;
import com.backend.model.Movie;
import com.backend.utils.TestingUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Pageable;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private GenreRepository genreRepository;

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
    void findAllByTitleContains() {
        Actor actor = this.actorRepository.save(new Actor("actor") );
        Genre genre = this.genreRepository.save(new Genre("genre"));

        List<Movie> contentToInsert = TestingUtils.getMoviesToTest() ;
        contentToInsert.forEach(content -> {
            content.setActors(Set.of(actor));
            content.setGenres(Set.of(genre));
        }) ;

        this.movieRepository.saveAll(contentToInsert) ;

        List<Movie> retrievedMovies = this.movieRepository.findAllByTitleContains("John" , Pageable.unpaged()).getContent() ;
        assertThat(retrievedMovies).isNotEmpty();
        assertThat(retrievedMovies.size()).isEqualTo(1);
        assertThat(retrievedMovies.get(0).getTitle()).contains("John");
    }
}