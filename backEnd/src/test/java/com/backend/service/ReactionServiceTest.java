package com.backend.service;


import com.backend.dto.ReactionRequest;
import com.backend.dto.ReactionResponse;
import com.backend.model.Content;
import com.backend.model.Rating;
import com.backend.model.Reaction;
import com.backend.model.User;
import com.backend.repository.ContentRepository;
import com.backend.repository.ReactionRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReactionServiceTest {
    @Mock
    private ReactionRepository reactionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ContentRepository contentRepository;
    @InjectMocks
    private ReactionService reactionService;

    @Test
    void reactToContentTest() {
        User user = User.builder()
                .id(0).lastName("mohamed").lastName("el afia")
                .build() ;

        Content content = TestingUtils.getContentsToTest().get(0) ;
        content.setId(0);

        Rating rating = Rating.builder()
                .content(content)
                .user(user)
                .date(Date.valueOf(LocalDate.now()))
                .time(Time.valueOf(LocalTime.now()))
                .build();
        ReactionRequest reactionRequest = ReactionRequest.builder()
                .contentId(rating.getContent().getId())
                .reactionType(ReactionType.DISLIKE)
                .userId(rating.getUser().getId())
                .build();

        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user)) ;
        when(contentRepository.findById(eq(content.getId()))).thenReturn(Optional.of(content)) ;
        when(reactionRepository.save(any(Reaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReactionResponse savedReaction = this.reactionService.reactToContent(reactionRequest);

        assertThat(savedReaction).isNotNull();
        assertThat(savedReaction.getDate()).isEqualTo(rating.getDate());
        assertThat(savedReaction.getTime()).isEqualTo(rating.getTime());
        assertThat(savedReaction.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedReaction.getReactionType()).isEqualTo(ReactionType.DISLIKE);
        assertThat(savedReaction.getContent().getId()).isEqualTo(rating.getContent().getId());
    }
}