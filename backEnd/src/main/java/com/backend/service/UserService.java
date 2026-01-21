package com.backend.service;


import com.backend.enums.HistoryType;
import com.backend.dto.AuthRequest;
import com.backend.dto.AuthResponse;
import com.backend.dto.History;
import com.backend.dto.UserInteractions;
import com.backend.exceptions.InvalidPasswordException;
import com.backend.exceptions.UsernameAlreadyTakenException;
import com.backend.model.*;
import com.backend.repository.*;
import com.backend.utils.ReactionType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;
    private final ReactionRepository reactionRepository;
    private final PasswordEncoder passwordEncoder;
    private JWTService jwtService;
    private UserDetailsService userDetailsService;

    public UserService(
            UserRepository userRepository ,
            RatingRepository ratingRepository ,
            ReviewRepository reviewRepository ,
            ReactionRepository reactionRepository ,
            PasswordEncoder passwordEncoder ,
            JWTService jwtService ,
            UserDetailsService userDetailsService
            ) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.reviewRepository = reviewRepository;
        this.reactionRepository = reactionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public AuthResponse register(User user) {
        if(this.userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException(user.getUsername() + " is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser =  this.userRepository.save(user);



        return AuthResponse.builder()
                .accessToken(this.jwtService.generateToken(savedUser))
                .build();
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        UserDetails user = this.userDetailsService.loadUserByUsername(authRequest.getUsername()) ;

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("invalid password") ;
        }

        return AuthResponse.builder().accessToken(this.jwtService.generateToken((User) user)).build() ;
    }



    public UserInteractions getUserInteractions(int userId ,  int contentId ) {
        Reaction reaction = reactionRepository.findByContent_IdAndUser_Id(contentId, userId).orElse(null);
        UserInteractions userInteractions = UserInteractions.builder()
                .rated(this.ratingRepository.findByContent_IdAndUser_Id(contentId, userId).isPresent())
                .reviewed(this.reviewRepository.findByContent_IdAndUser_Id(contentId, userId).isPresent())
                .liked( reaction != null && reaction.getReactionType().equals(ReactionType.LIKE) )
                .disliked(reaction != null && reaction.getReactionType().equals(ReactionType.DISLIKE) )
                .build();


        return userInteractions;
    }

    public List<History> getUserHistory(int userId) {
        List<Review> reviews = this.reviewRepository.findAllByUser_Id(userId);
        List<Rating> ratings = this.ratingRepository.findAllByUser_Id(userId);
        List<Reaction> reactions = this.reactionRepository.findAllByUser_Id(userId);

        List<History> history = new ArrayList<>();
        history.addAll(reviews.stream().map(
                review -> History.builder()
                        .contentTitle(review.getContent().getTitle())
                        .date(review.getDate())
                        .time(review.getTime())
                        .historyType(HistoryType.REVIEW)
                        .contentId(review.getContent().getId())
                        .build()
        ).toList());

        history.addAll(ratings.stream().map(
                rating -> History.builder()
                        .contentTitle(rating.getContent().getTitle())
                        .date(rating.getDate())
                        .time(rating.getTime())
                        .historyType(HistoryType.RATING)
                        .contentId(rating.getContent().getId())
                        .build()
                ).toList()
        ) ;

        history.addAll(reactions.stream().map(
                        reaction -> History.builder()
                                .contentTitle(reaction.getContent().getTitle())
                                .date(reaction.getDate())
                                .time(reaction.getTime())
                                .historyType(reaction.getReactionType().equals(ReactionType.LIKE) ? HistoryType.LIKE : HistoryType.DISLIKE)
                                .contentId(reaction.getContent().getId())
                                .build()
                ).toList()
        ) ;

        return history   ;
    }

    public List<Integer> getUserPreferences(int userId) {
        List<Integer> preferences = new ArrayList<>();


        preferences.addAll(
                this.ratingRepository.findAllByUser_Id(userId)
                        .stream()
                        .filter(rating -> rating.getGivingRate() > 5 )
                        .map(rating -> rating.getContent().getId()).toList()) ;

        preferences.addAll(
                this.reviewRepository.findAllByUser_Id(userId)
                        .stream()
                        .filter(review -> review.isPositive())
                        .map(review -> review.getContent().getId()).toList()
        ) ;

        preferences.addAll(
                this.reactionRepository.findAllByUser_Id(userId)
                        .stream()
                        .filter(reaction -> reaction.getReactionType().equals(ReactionType.LIKE))
                        .map(reaction -> reaction.getContent().getId()).toList()
        ) ;
        return preferences ;
    }

}
