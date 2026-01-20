package com.backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SeriesRepositoryTest {
    @Autowired
    private SeriesRepository seriesRepository;

    @Test
    void findAllByTitleContains() {
    }
}