package com.backend.dto;


import com.backend.utils.ReactionType;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReactionRequest {
    private int contentId;
    private int userId ;
    private ReactionType reactionType;
}
