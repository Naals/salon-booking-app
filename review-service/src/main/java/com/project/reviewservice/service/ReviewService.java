package com.project.reviewservice.service;

import com.project.reviewservice.modal.Review;
import com.project.reviewservice.payload.dto.SalonDto;
import com.project.reviewservice.payload.dto.UserDto;
import com.project.reviewservice.payload.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(ReviewRequest request,
                        UserDto userDto,
                        SalonDto salonDto);

    Review getReviewById(Long id);

    List<Review> getReviewsBySalonId(Long salonId);

    Review updateReview(ReviewRequest req, Long reviewId, Long userId) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;
}
