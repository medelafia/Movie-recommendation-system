package com.backend.repository;


import com.backend.model.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Integer> {

    public Page<Content> findAllByTitleContains(String title , Pageable pageable);
}
