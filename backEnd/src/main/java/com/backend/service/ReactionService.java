package com.backend.service;


import com.backend.dto.ReactionRequest;
import com.backend.dto.ReactionResponse;
import com.backend.dto.UserActionEvent;
import com.backend.dto.UserResponse;
import com.backend.model.Reaction;
import com.backend.repository.ContentRepository;
import com.backend.repository.ReactionRepository;
import com.backend.repository.UserRepository;
import com.backend.utils.ReactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final EventService eventService;

    public ReactionService(ReactionRepository reactionRepository,ContentRepository contentRepository , UserRepository userRepository , EventService eventService ) {
        this.reactionRepository = reactionRepository;
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
        this.eventService = eventService;
    }




    @Transactional
    public ReactionResponse reactToContent(ReactionRequest reactionRequest) {
        Reaction reaction = Reaction.builder()
                .content(this.contentRepository.findById(reactionRequest.getContentId()).orElseThrow())
                .reactionType(reactionRequest.getReactionType())
                .user(this.userRepository.findById(reactionRequest.getUserId()).orElseThrow())
                .time(Time.valueOf(LocalTime.now()))
                .date(Date.valueOf(LocalDate.now()))
                .build();

        if(reactionRequest.getReactionType().equals(ReactionType.LIKE) ) {
            this.eventService.sendEvent(
                    UserActionEvent.builder()
                            .contentId(reactionRequest.getContentId())
                            .userId(reactionRequest.getUserId())
                            .build()
            ) ;
        }

        reaction = this.reactionRepository.save(reaction);

        return ReactionResponse.builder()
                .content(reaction.getContent())
                .reactionType(reaction.getReactionType())
                .id(reaction.getId())
                .date(reaction.getDate())
                .time(reaction.getTime())
                .user(
                        UserResponse.builder()
                                .id(reaction.getUser().getId())
                                .username(reaction.getUser().getUsername())
                                .firstName(reaction.getUser().getFirstName())
                                .lastName(reaction.getUser().getLastName())
                                .build()
                )
                .build();
    }


}
