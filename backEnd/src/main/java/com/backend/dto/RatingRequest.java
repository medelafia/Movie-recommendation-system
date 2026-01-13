package com.backend.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RatingRequest {
    private int userId ;
    private int contentId ;
    private float rating;
}
