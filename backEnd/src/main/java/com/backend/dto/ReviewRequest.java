package com.backend.dto;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private int contentId;
    private int userId;
    private String content;
    private boolean positive ;
}
