package com.backend.service;

import com.backend.dto.RatingRequest;
import com.backend.dto.RatingResponse;
import com.backend.model.Content;
import com.backend.model.Rating;
import com.backend.model.User;
import com.backend.repository.ContentRepository;
import com.backend.repository.RatingRepository;
import com.backend.repository.UserRepository;
import com.backend.utils.TestingUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ContentRepository contentRepository;
    @InjectMocks
    private RatingService ratingService;
    @Test
    public void saveRatingTest() {
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
        RatingRequest ratingRequest = RatingRequest.builder()
                .contentId(rating.getContent().getId())
                .rating(7.0f)
                .userId(rating.getUser().getId())
                .build();

        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user)) ;
        when(contentRepository.findById(eq(content.getId()))).thenReturn(Optional.of(content)) ;
        when(ratingRepository.save(any(Rating.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RatingResponse savedRating = this.ratingService.saveRating(ratingRequest);

        assertThat(savedRating).isNotNull();
        assertThat(savedRating.getDate()).isEqualTo(rating.getDate());
        assertThat(savedRating.getTime()).isEqualTo(rating.getTime());
        assertThat(savedRating.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedRating.getContent().getId()).isEqualTo(rating.getContent().getId());

        verify(this.ratingRepository , times(1)).save(any(Rating.class));
    }
}