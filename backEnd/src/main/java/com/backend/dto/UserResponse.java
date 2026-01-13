package com.backend.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private int id ;
    private String username ;
    private String firstName ;
    private String lastName ;
}
