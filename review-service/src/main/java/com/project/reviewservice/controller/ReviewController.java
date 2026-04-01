package com.project.reviewservice.controller;


import com.project.reviewservice.modal.Review;
import com.project.reviewservice.payload.dto.SalonDto;
import com.project.reviewservice.payload.dto.UserDto;
import com.project.reviewservice.payload.request.ReviewRequest;
import com.project.reviewservice.payload.response.ApiResponse;
import com.project.reviewservice.service.ReviewService;
import com.project.reviewservice.service.client.SalonFeignClient;
import com.project.reviewservice.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserFeignClient userFeignClient;
    private final SalonFeignClient salonFeignClient;

    @PostMapping("/salon/{salonId}")
    public ResponseEntity<Review> createReview(
            @PathVariable Long salonId,
            @RequestBody ReviewRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserDto user = userFeignClient.getUserProfile(jwt).getBody();
        SalonDto salon = salonFeignClient.getSalonById(salonId).getBody();

        Review review = reviewService.createReview(req, user, salon);

        return ResponseEntity.ok(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserDto user = userFeignClient.getUserProfile(jwt).getBody();

        Review reviews = reviewService.updateReview(
                req,
                reviewId,
                user.getId()
        );

        return ResponseEntity.ok(reviews);
    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserDto user = userFeignClient.getUserProfile(jwt).getBody();

        reviewService.deleteReview(reviewId, user.getId());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Review deleted");

        return ResponseEntity.ok(apiResponse);
    }

}
