package com.backend.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInteractions {
    private boolean liked ;
    private boolean disliked ;
    private boolean rated ;
    private boolean reviewed ;
}
