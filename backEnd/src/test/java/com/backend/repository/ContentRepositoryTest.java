package com.backend.repository;

import com.backend.model.Actor;
import com.backend.model.Content;
import com.backend.model.Genre;
import com.backend.model.Movie;
import com.backend.utils.TestingUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
class ContentRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ContentRepository contentRepository;

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

        List<Content> retrievedContent = this.contentRepository.findAllByTitleContains("John" , Pageable.unpaged()).getContent() ;
        assertThat(retrievedContent).isNotEmpty();
        assertThat(retrievedContent.size()).isEqualTo(1);
        assertThat(retrievedContent.get(0).getTitle()).contains("John");
    }
}