package com.viet.controller;

import com.viet.model.Product;
import com.viet.model.Review;
import com.viet.model.User;
import com.viet.request.CreateReviewRequest;
import com.viet.response.ApiResponse;
import com.viet.service.Impl.ProductService;
import com.viet.service.Impl.ReviewService;
import com.viet.service.Impl.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {

    ReviewService reviewService;
    UserService userService;
    ProductService productService;

    @GetMapping("/product/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) throws Exception {

        List<Review> reviews = reviewService.getReviewByProductId(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> createReview(@PathVariable Long productId,
                                               @RequestHeader("Authorization") String jwt,
                                               @RequestBody CreateReviewRequest req) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Product product = productService.findProductById(productId);

        Review review = reviewService.createReview(req, user, product);

        return new ResponseEntity<>(review, HttpStatus.CREATED);

    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,
                                               @RequestHeader("Authorization") String jwt,
                                               @RequestBody CreateReviewRequest req) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Review review = reviewService.updateReview(reviewId, req.getReviewText(), user.getId(), req.getReviewRating());

        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        reviewService.deleteReview(reviewId, user.getId());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Review deleted successfully");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
