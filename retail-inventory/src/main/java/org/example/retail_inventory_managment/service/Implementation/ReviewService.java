package org.example.retail_inventory_managment.service.Implementation;

import lombok.RequiredArgsConstructor;
import org.example.retail_inventory_managment.dto.requestDTO.ModerateReviewRequest;
import org.example.retail_inventory_managment.dto.requestDTO.ReviewRequest;
import org.example.retail_inventory_managment.dto.resposneDTO.ProductReviewSummary;
import org.example.retail_inventory_managment.dto.resposneDTO.ReviewResponse;
import org.example.retail_inventory_managment.entity.*;
import org.example.retail_inventory_managment.enums.ModerationAction;
import org.example.retail_inventory_managment.enums.ReviewStatus;
import org.example.retail_inventory_managment.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Service @RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewModerationRepository moderationRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponse submit(ReviewRequest request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (reviewRepository.findByProductIdAndUserId(product.getId(), user.getId()).isPresent()) throw new IllegalStateException("User has already reviewed this product");
        Review review = Review.builder().product(product).user(user).rating(request.getRating()).reviewText(request.getReviewText().trim()).submittedDate(Instant.now()).status(ReviewStatus.PENDING_MODERATION).build();
        return toResponse(reviewRepository.save(review));
    }

    @Transactional
    public ReviewResponse moderate(Long reviewId, ModerateReviewRequest request, String moderatorEmail) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Review not found"));
        User moderator = userRepository.findByEmail(moderatorEmail).orElseThrow(() -> new IllegalArgumentException("Moderator not found"));
        ReviewStatus status = switch (request.getAction()) { case APPROVED -> ReviewStatus.PUBLISHED; case REJECTED -> ReviewStatus.REJECTED; case FLAGGED -> ReviewStatus.FLAGGED; };
        review.setStatus(status); review.setModeratedBy(moderator); review.setModeratedDate(Instant.now());
        moderationRepository.save(ReviewModeration.builder().review(review).moderator(moderator).action(request.getAction()).comments(request.getComments()).moderatedDate(Instant.now()).build());
        return toResponse(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> productReviews(Long productId) { return reviewRepository.findByProductIdAndStatusOrderBySubmittedDateDesc(productId, ReviewStatus.PUBLISHED).stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true)
    public List<ReviewResponse> userReviews(Long userId) { return reviewRepository.findByUserIdOrderBySubmittedDateDesc(userId).stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true)
    public List<ReviewResponse> pendingReviews() { return reviewRepository.findByStatusOrderBySubmittedDateAsc(ReviewStatus.PENDING_MODERATION).stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true)
    public ProductReviewSummary summary(Long productId) { Object[] result = reviewRepository.publishedSummary(productId); BigDecimal avg = result[0] == null ? BigDecimal.ZERO : new BigDecimal(result[0].toString()).setScale(2, RoundingMode.HALF_UP); return ProductReviewSummary.builder().averageRating(avg).reviewCount(((Number) result[1]).longValue()).build(); }

    private ReviewResponse toResponse(Review review) { return ReviewResponse.builder().id(review.getId()).productId(review.getProduct().getId()).productName(review.getProduct().getName()).userId(review.getUser().getId()).userName(review.getUser().getFirstName() + " " + review.getUser().getLastName()).rating(review.getRating()).reviewText(review.getReviewText()).submittedDate(review.getSubmittedDate()).moderatedDate(review.getModeratedDate()).status(review.getStatus()).build(); }
}
