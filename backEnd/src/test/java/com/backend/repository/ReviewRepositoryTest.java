package com.backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void findAllByContent() {
    }

    @Test
    void findByContent_IdAndUser_Id() {
    }

    @Test
    void findAllByUser_Id() {
    }
}