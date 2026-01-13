package com.backend.repository;


import com.backend.model.Rating;
import com.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    public Optional<Rating> findByContent_IdAndUser_Id(int contentId, int userId);
    public List<Rating> findAllByUser_Id(int userId);
}
