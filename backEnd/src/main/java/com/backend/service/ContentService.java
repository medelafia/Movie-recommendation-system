package com.backend.service;


import com.backend.model.*;
import com.backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class ContentService {

    private final ContentRepository contentRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final SeriesRepository seriesRepository;

    public ContentService(ContentRepository contentRepository , GenreRepository genreRepository , ActorRepository actorRepository  , MovieRepository movieRepository , SeriesRepository seriesRepository) {
        this.contentRepository = contentRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.seriesRepository = seriesRepository;
    }



    public Page<Content> findAllByTitleContains(String title, Pageable pageable) {
        return this.contentRepository.findAllByTitleContains( title , pageable );
    }

    public Page<Movie> findAllMoviesByTitleContains(String searchKey, Pageable pageable) {
        return this.movieRepository.findAllByTitleContains( searchKey , pageable );
    }
    public Page<Series> findAllSeriesByTitleContains(String searchKey, Pageable pageable) {
        return this.seriesRepository.findAllByTitleContains( searchKey , pageable );
    }
    public Page<Movie> findAllMovies(Pageable pageable) {
        return this.movieRepository.findAll(pageable);
    }
    public Page<Series> findAllSeries(Pageable pageable) {
        return this.seriesRepository.findAll(pageable);
    }

    public Page<Content> findAll(Pageable pageable) {
        return this.contentRepository.findAll(pageable);
    }

    @Transactional
    public Content save(Content content) {
        HashSet<Genre> genres = new HashSet<>() ;
        for(Genre genre : content.getGenres()) {
            genres.add(this.genreRepository.findById(genre.getName()).orElseGet(()->this.genreRepository.save(genre)));
        }
        content.setGenres(genres);

        HashSet<Actor> actors = new HashSet<>() ;
        for(Actor actor : content.getActors()) {
            actors.add(this.actorRepository.findById(actor.getName()).orElseGet(()->this.actorRepository.save(actor)));
        }
        content.setActors(actors);

        return this.contentRepository.save(content);
    }

    public Content findById(int id) {
        return this.contentRepository.findById(id).orElseThrow(()->new RuntimeException("No Element found")) ;
    }


    public List<Content> findAllByIds(ArrayList<Integer> ids) {
        return this.contentRepository.findAllById(ids);
    }
}
