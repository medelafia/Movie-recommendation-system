package com.backend.service;

import com.backend.dto.ReactionRequest;
import com.backend.dto.ReactionResponse;
import com.backend.dto.ReviewRequest;
import com.backend.dto.ReviewResponse;
import com.backend.model.*;
import com.backend.repository.ContentRepository;
import com.backend.repository.ReviewRepository;
import com.backend.repository.UserRepository;
import com.backend.utils.ReactionType;
import com.backend.utils.TestingUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ContentRepository contentRepository;
    @InjectMocks
    private ReviewService reviewService;

    @Test
    void saveReviewTest() {
        User user = User.builder()
                .id(0).lastName("mohamed").lastName("el afia")
                .build() ;
        Content content = TestingUtils.getContentsToTest().get(0) ;
        Rating rating = Rating.builder()
                .content(content)
                .user(user)
                .date(Date.valueOf(LocalDate.now()))
                .time(Time.valueOf(LocalTime.now()))
                .build();
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .contentId(rating.getContent().getId())
                .positive(false)
                .content("Review Content")
                .userId(rating.getUser().getId())
                .build();

        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user)) ;
        when(contentRepository.findById(eq(content.getId()))).thenReturn(Optional.of(content)) ;
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReviewResponse savedReview = this.reviewService.saveReview(reviewRequest);

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getDate()).isEqualTo(rating.getDate());
        assertThat(savedReview.getTime()).isEqualTo(rating.getTime());
        assertThat(savedReview.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedReview.getReviewContent()).isEqualTo(rating.getContent());
        assertThat(savedReview.isPositive()).isFalse();
        assertThat(savedReview.getContent().getId()).isEqualTo(rating.getContent().getId());
    }
}