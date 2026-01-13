package com.backend.repository;

import com.backend.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findAllByTitleContains(String title, Pageable pageable);
}
