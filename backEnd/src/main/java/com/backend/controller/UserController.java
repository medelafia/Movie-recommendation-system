package com.backend.controller;


import com.backend.dto.AuthRequest;
import com.backend.dto.AuthResponse;
import com.backend.dto.History;
import com.backend.dto.UserInteractions;
import com.backend.model.User;
import com.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:4200" )
public class UserController {

    private final UserService userService;



    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.authenticate(authRequest)) ;
    }
    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.register(user)) ;
    }
    @GetMapping("/{userId}/interactions")
    public UserInteractions getInteractions(@PathVariable int userId , @RequestParam int contentId) {
        return this.userService.getUserInteractions(userId, contentId);
    }

    @GetMapping("/{userId}/history")
    public List<History> getAllUserHistories(@PathVariable int userId) {
        return this.userService.getUserHistory(userId);
    }


    @GetMapping("/{userId}/preferences")
    public List<Integer> getUserPreferences(@PathVariable int userId) {
        return this.userService.getUserPreferences(userId) ;
    }

}
