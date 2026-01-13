package com.backend.repository;

import com.backend.model.Content;
import com.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByContent(Content content);
    Optional<Review> findByContent_IdAndUser_Id(int contentId , int userId);
    List<Review> findAllByUser_Id(int userId);
}
