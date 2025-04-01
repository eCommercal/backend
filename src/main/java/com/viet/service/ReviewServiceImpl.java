package com.viet.service;

import com.viet.model.Product;
import com.viet.model.Review;
import com.viet.model.User;
import com.viet.repository.ReviewRepository;
import com.viet.request.CreateReviewRequest;
import com.viet.service.Impl.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;

    @Override
    public Review createReview(CreateReviewRequest req, User user, Product product) {
        Review review = new Review();

        review.setUser(user);
        review.setProduct(product);
        review.setReviewText(req.getReviewText());
        review.setRating(req.getReviewRating());
        review.setProductImages(req.getProductImages());

        product.getReviews().add(review);

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, Long userId, double rating) throws Exception {

        Review review = this.getReviewById(reviewId);

        if (review.getUser().getId().equals(userId)) {
            review.setReviewText(reviewText);
            review.setRating(rating);
            return reviewRepository.save(review);
        }

        throw new Exception("you can't update this review");
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {

        Review review = this.getReviewById(reviewId);

        if(review.getUser().getId().equals(userId)) {
            throw new Exception("review cannot be deleted");
        }
//        review.getProduct().getReviews().remove(review);

        reviewRepository.delete(review);
    }

    @Override
    public Review getReviewById(Long reviewId) throws Exception {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new Exception("review not found with id - " + reviewId));
    }
}
