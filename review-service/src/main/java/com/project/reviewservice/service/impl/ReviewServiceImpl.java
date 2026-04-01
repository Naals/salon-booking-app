package com.project.reviewservice.service.impl;

import com.project.reviewservice.modal.Review;
import com.project.reviewservice.payload.dto.SalonDto;
import com.project.reviewservice.payload.dto.UserDto;
import com.project.reviewservice.payload.request.ReviewRequest;
import com.project.reviewservice.repository.ReviewRepository;
import com.project.reviewservice.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(ReviewRequest request, UserDto userDto, SalonDto salonDto) {

        Review review = new Review();
        review.setReviewText(request.getReviewText());
        review.setRating(request.getRating());
        review.setUserId(userDto.getId());
        review.setSalonId(salonDto.getId());

        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review not found"));
    }

    @Override
    public List<Review> getReviewsBySalonId(Long salonId) {
        return reviewRepository.findBySalonId(salonId);
    }

    @Override
    public Review updateReview(ReviewRequest req, Long reviewId, Long userId) throws Exception {
        Review review = getReviewById(reviewId);

        if(!review.getUserId().equals(userId)){
            throw new Exception("you don't have permission to update this review");
        }

        review.setReviewText(req.getReviewText());
        review.setRating(req.getRating());

        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {
        Review review = getReviewById(reviewId);
        if(!review.getUserId().equals(userId)){
            throw new Exception("you don't have permission to delete this review");
        }
        reviewRepository.deleteById(reviewId);
    }
}
