package com.backend.service;

import com.backend.model.*;
import com.backend.repository.*;
import com.backend.utils.TestingUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {
    @Mock
    private ContentRepository contentRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private ActorRepository actorRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private SeriesRepository seriesRepository;
    @InjectMocks
    private ContentService contentService;





    @Test
    void saveTest() {
        Content content = Content.builder().title("title")
                .overview("overview")
                .genres(Set.of(new Genre("genre")))
                .actors(Set.of(new Actor("actor")))
                .build();

        when(contentRepository.save(any(Content.class)))
                .thenAnswer(invo -> invo.getArgument(0)) ;

        Content savedContent = contentService.save(content);

        assertThat(savedContent.getTitle()).isEqualTo(content.getTitle());
        assertThat(savedContent.getId()).isEqualTo(content.getId());

        verify(contentRepository  , times(1)).save(content);
    }
    @Test
    void findAllByTitleContainsTest() {
        List<Content> contents = TestingUtils.getContentsToTest();

        Page<Content> contentPage = new PageImpl<>(List.of(contents.get(0)));

        when(contentRepository.findAllByTitleContains(eq("bad") , any(Pageable.class)))
                .thenReturn(contentPage) ;

        List<Content> contentList = this.contentService.findAllByTitleContains("bad" , Pageable.unpaged()).getContent();

        assertThat(contentList).isNotNull()  ;
        assertThat(contentList).hasSize(1);
    }

    @Test
    void findAllMoviesByTitleContainsTest() {
        Page<Movie> moviePage = new PageImpl<>(List.of(TestingUtils.getMoviesToTest().get(1)));

        given(this.movieRepository.findAllByTitleContains(eq("8") , any(Pageable.class))).willReturn(moviePage);

        List<Movie> retrievedMovies = this.contentService.findAllMoviesByTitleContains("8" , Pageable.unpaged()).getContent();
        assertThat(retrievedMovies).isNotNull()  ;
        assertThat(retrievedMovies).hasSize(1);
        assertThat(retrievedMovies.get(0).getTitle()).isEqualTo(TestingUtils.getMoviesToTest().get(1).getTitle());
    }

    @Test
    void findAllSeriesByTitleContainsTest() {
        Page<Series> seriesPage = new PageImpl<>(List.of(TestingUtils.getSeriesToTest().get(0)));

        given(this.seriesRepository.findAllByTitleContains(eq("bad") , any(Pageable.class))).willReturn(seriesPage)  ;

        List<Series> retrievedSeries = this.contentService.findAllSeriesByTitleContains("bad" , Pageable.unpaged()).getContent();
        assertThat(retrievedSeries).isNotNull()  ;
        assertThat(retrievedSeries).hasSize(1);
        assertThat(retrievedSeries.get(0).getTitle()).isEqualTo(TestingUtils.getSeriesToTest().get(0).getTitle());
    }

    @Test
    void findAllMoviesTest() {
        given(this.movieRepository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(TestingUtils.getMoviesToTest()));
        List<Movie> retrievedMovies = this.contentService.findAllMovies(Pageable.unpaged()).getContent() ;

        assertThat(retrievedMovies).isNotNull()  ;
        assertThat(retrievedMovies).hasSize(2);
        assertThat(retrievedMovies.get(0).getTitle()).isEqualTo(TestingUtils.getMoviesToTest().get(0).getTitle());
    }

    @Test
    void findAllSeriesTest() {
        given(this.seriesRepository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(TestingUtils.getSeriesToTest()));
        List<Series> retrievedSeries = this.contentService.findAllSeries(Pageable.unpaged()).getContent() ;
        assertThat(retrievedSeries).isNotNull()  ;
        assertThat(retrievedSeries).hasSize(2);
        assertThat(retrievedSeries.get(0).getTitle()).isEqualTo(TestingUtils.getSeriesToTest().get(0).getTitle());
    }

    @Test
    void findAllTest() {
        given(this.contentRepository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(TestingUtils.getContentsToTest()) ) ;

        List<Content> retrievedContents = this.contentService.findAll(Pageable.unpaged()).getContent();
        assertThat(retrievedContents).isNotNull()  ;
        assertThat(retrievedContents).hasSize(3);
        assertThat(retrievedContents.get(0).getTitle()).isEqualTo(TestingUtils.getContentsToTest().get(0).getTitle());
    }


    @Test
    void findByIdTest() {
        given(this.contentRepository.findById(any(Integer.class))).willReturn(Optional.of(TestingUtils.getContentsToTest().get(0)));

        Content content = this.contentService.findById(0) ;
        assertThat(content).isNotNull()  ;
        assertThat(content.getTitle()).isEqualTo(TestingUtils.getContentsToTest().get(0).getTitle());
    }

    @Test
    void findAllByIdsTest() {
        given(this.contentRepository.findAllById(any(List.class))).willReturn(List.of(TestingUtils.getContentsToTest().get(0) , TestingUtils.getContentsToTest().get(1)));

        List<Content> retrievedContent = this.contentService.findAllByIds(new ArrayList<>(List.of(0 , 1))) ;
        assertThat(retrievedContent).isNotNull()  ;
        assertThat(retrievedContent).hasSize(2);
        assertThat(retrievedContent.get(0).getTitle()).isEqualTo(TestingUtils.getContentsToTest().get(0).getTitle());

    }
}