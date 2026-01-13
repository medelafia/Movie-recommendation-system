package com.backend.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserActionEvent {
    private int userId  ;
    private int contentId ;
}
