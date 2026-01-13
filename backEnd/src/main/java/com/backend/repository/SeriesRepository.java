package com.backend.repository;


import com.backend.model.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, String > {
    public Page<Series> findAllByTitleContains(String searchKey , Pageable page);
}
