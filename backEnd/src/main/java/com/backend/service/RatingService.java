package com.backend.service;


import com.backend.dto.RatingResponse;
import com.backend.dto.UserActionEvent;
import com.backend.dto.RatingRequest;
import com.backend.dto.UserResponse;
import com.backend.model.Rating;
import com.backend.repository.ContentRepository;
import com.backend.repository.RatingRepository;
import com.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final EventService eventService;

    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, ContentRepository contentRepository , EventService eventService) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
        this.eventService = eventService;
    }

    @Transactional
    public RatingResponse saveRating(RatingRequest ratingRequest) {
        Rating rating = Rating.builder()
                .givingRate(ratingRequest.getRating())
                .content(this.contentRepository.findById(ratingRequest.getContentId()).orElseThrow())
                .date(Date.valueOf(LocalDate.now()))
                .time(Time.valueOf(LocalTime.now()))
                .user(this.userRepository.findById(ratingRequest.getUserId()).orElseThrow())
                .build();


        if(ratingRequest.getRating() > 7) {
            this.eventService.sendEvent(
                UserActionEvent
                        .builder()
                        .contentId(ratingRequest.getContentId())
                        .userId(ratingRequest.getUserId())
                        .build()
            );
        }
        rating = this.ratingRepository.save(rating);

        return RatingResponse.builder()
                .id(rating.getId())
                .givingRate(rating.getGivingRate())
                .content(rating.getContent())
                .date(rating.getDate())
                .time(rating.getTime())
                .user(
                        UserResponse.builder()
                                .username(rating.getUser().getUsername())
                                .lastName(rating.getUser().getLastName())
                                .firstName(rating.getUser().getFirstName())
                                .id(rating.getUser().getId())
                                .build()
                )
                .build();
    }

}
