package org.example.retail_inventory_managment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.retail_inventory_managment.dto.requestDTO.ModerateReviewRequest;
import org.example.retail_inventory_managment.dto.requestDTO.ReviewRequest;
import org.example.retail_inventory_managment.dto.resposneDTO.ProductReviewSummary;
import org.example.retail_inventory_managment.dto.resposneDTO.ReviewResponse;
import org.example.retail_inventory_managment.service.Implementation.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/reviews") @RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping public ResponseEntity<ReviewResponse> submit(@Valid @RequestBody ReviewRequest request, Authentication authentication) { return ResponseEntity.ok(reviewService.submit(request, authentication.getName())); }
    @GetMapping("/product/{productId}") public ResponseEntity<List<ReviewResponse>> productReviews(@PathVariable Long productId) { return ResponseEntity.ok(reviewService.productReviews(productId)); }
    @GetMapping("/product/{productId}/summary") public ResponseEntity<ProductReviewSummary> summary(@PathVariable Long productId) { return ResponseEntity.ok(reviewService.summary(productId)); }
    @GetMapping("/user/{userId}") public ResponseEntity<List<ReviewResponse>> userReviews(@PathVariable Long userId) { return ResponseEntity.ok(reviewService.userReviews(userId)); }
    @GetMapping("/pending") public ResponseEntity<List<ReviewResponse>> pending() { return ResponseEntity.ok(reviewService.pendingReviews()); }
    @PutMapping("/{reviewId}/moderate") public ResponseEntity<ReviewResponse> moderate(@PathVariable Long reviewId, @Valid @RequestBody ModerateReviewRequest request, Authentication authentication) { return ResponseEntity.ok(reviewService.moderate(reviewId, request, authentication.getName())); }
}
