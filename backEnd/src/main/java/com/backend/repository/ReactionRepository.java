package com.backend.repository;


import com.backend.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByContent_IdAndUser_Id(int contentId , int userId);
    List<Reaction> findAllByUser_Id(int userId);
}
