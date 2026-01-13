package com.backend.controller;

import com.backend.dto.*;
import com.backend.model.*;
import com.backend.service.ContentService;
import com.backend.service.RatingService;
import com.backend.service.ReactionService;
import com.backend.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/content")
@CrossOrigin("http://localhost:4200")
public class ContentController {

    private final ContentService contentService;
    private final ReviewService reviewService;
    private final RatingService ratingService;
    private final ReactionService reactionService;

    public ContentController(ContentService contentService , ReviewService reviewService , RatingService ratingService , ReactionService reactionService ) {
        this.contentService = contentService;
        this.reviewService = reviewService;
        this.ratingService = ratingService;
        this.reactionService = reactionService;
    }

    @GetMapping("/")
    public Page<Content> getAll(
            @RequestParam(defaultValue = "0" , required = false) int page ,
            @RequestParam(defaultValue = "30" , required = false ) int size ,
            @RequestParam(required = false)String searchKey ,
            @RequestParam(required = false)String type
    ) {
        if(searchKey != null && !searchKey.isEmpty())
            return this.contentService.findAllByTitleContains(searchKey, Pageable.ofSize(size).withPage(page));

        return this.contentService.findAll(Pageable.ofSize(size).withPage(page)) ;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<Content> getContentById(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(this.contentService.findById(id)) ;
    }

    @GetMapping("/{id}/reviews")
    public List<ReviewResponse> getContentReviews(@PathVariable int id) {
        return this.reviewService.getAllReviewsByContentId(id) ;
    }

    @PostMapping("/saveReview")
    public ResponseEntity<ReviewResponse> addReview(@RequestBody ReviewRequest review) {
        return ResponseEntity.status(HttpStatus.OK).body(this.reviewService.saveReview(review)) ;
    }

    @PostMapping("/saveRating")
    public ResponseEntity<RatingResponse> addRating(@RequestBody RatingRequest rating) {
        return ResponseEntity.status(HttpStatus.OK).body(this.ratingService.saveRating(rating)) ;
    }
    @PostMapping("/saveReaction")
    public ResponseEntity<ReactionResponse> addReaction(@RequestBody ReactionRequest reaction) {
        return ResponseEntity.status(HttpStatus.OK).body(this.reactionService.reactToContent(reaction) );
    }

    @PostMapping("/series")
    public Content addSeries(@RequestBody Series series) {
        return this.contentService.save(series );
    }
    @PostMapping("/movies")
    public Content addMovies(@RequestBody Movie movie) {
        return this.contentService.save(movie );
    }

    @GetMapping("/all")
    public List<Content> getAllContent(@RequestParam ArrayList<Integer> ids) {
        return this.contentService.findAllByIds(ids) ;
    }

}