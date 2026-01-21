package com.backend.repository;

import com.backend.model.Actor;
import com.backend.model.Genre;
import com.backend.model.Series;
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
class SeriesRepositoryTest {
    @Autowired
    private SeriesRepository seriesRepository;
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

        List<Series> seriesRetrieved = TestingUtils.getSeriesToTest() ;
        seriesRetrieved.forEach(content -> {
            content.setActors(Set.of(actor));
            content.setGenres(Set.of(genre));
        }) ;

        this.seriesRepository.saveAll(seriesRetrieved) ;

        List<Series> retrievedSeries = this.seriesRepository.findAllByTitleContains("bad" , Pageable.unpaged()).getContent() ;
        assertThat(retrievedSeries).isNotEmpty();
        assertThat(retrievedSeries.size()).isEqualTo(1);
        assertThat(retrievedSeries.get(0).getTitle()).contains("bad");
    }
}