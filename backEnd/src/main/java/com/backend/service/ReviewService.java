package com.backend.service;

import com.backend.dto.ReviewRequest;
import com.backend.dto.ReviewResponse;
import com.backend.dto.UserActionEvent;
import com.backend.dto.UserResponse;
import com.backend.model.Review;
import com.backend.repository.ContentRepository;
import com.backend.repository.ReviewRepository;
import com.backend.repository.UserRepository;
import com.backend.utils.ReactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final EventService eventService;

    public ReviewService(ReviewRepository reviewRepository , ContentRepository contentRepository , UserRepository userRepository , EventService eventService) {
        this.reviewRepository = reviewRepository;
        this.contentRepository = contentRepository;
        this.userRepository = userRepository ;
        this.eventService = eventService;
    }

    public List<ReviewResponse> getAllReviewsByContentId(int contentId) {
        return this.reviewRepository
                .findAllByContent(
                        this.contentRepository.findById(contentId).orElseThrow(() -> new RuntimeException("cannot find the movie"))
                )
                .stream()
                .map(
                        review -> ReviewResponse.builder()
                                .id(review.getId())
                                .reviewContent(review.getReviewContent())
                                .positive(review.isPositive())
                                .user(UserResponse.builder()
                                        .firstName(review.getUser().getFirstName())
                                        .lastName(review.getUser().getLastName())
                                        .username(review.getUser().getUsername())
                                        .build())
                                .content(review.getContent())
                                .time(review.getTime())
                                .date(review.getDate())
                                .build()
                )
                .toList();
    }


    @Transactional
    public ReviewResponse saveReview(ReviewRequest reviewRequest ) {
        Review review = Review.builder()
                .content(this.contentRepository.findById(reviewRequest.getContentId()).orElseThrow(() -> new RuntimeException("cannot find the movie")))
                .user(this.userRepository.findById(reviewRequest.getUserId()).orElseThrow(() -> new RuntimeException("cannot find the user")))
                .date(Date.valueOf(LocalDate.now()))
                .time(Time.valueOf(LocalTime.now()))
                .reviewContent(reviewRequest.getContent())
                .positive(reviewRequest.isPositive())
                .build();

        if(review.isPositive()) {
            this.eventService.sendEvent(
                    UserActionEvent.builder()
                            .contentId(reviewRequest.getContentId())
                            .userId(reviewRequest.getUserId())
                            .build()
            ) ;
        }

        review = this.reviewRepository.save(review);


        return ReviewResponse.builder()
                .id(review.getId())
                .reviewContent(review.getReviewContent())
                .positive(review.isPositive())
                .user(
                        UserResponse.builder()
                                .username(review.getUser().getUsername())
                                .firstName(review.getUser().getFirstName())
                                .lastName(review.getUser().getLastName())
                                .build()
                )
                .time(review.getTime())
                .date(review.getDate())
                .content(review.getContent())
                .build();
    }
}
