package org.example.retail_inventory_managment.repository;

import org.example.retail_inventory_managment.entity.Review;
import org.example.retail_inventory_managment.enums.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByProductIdAndUserId(Long productId, Long userId);
    List<Review> findByProductIdAndStatusOrderBySubmittedDateDesc(Long productId, ReviewStatus status);
    List<Review> findByUserIdOrderBySubmittedDateDesc(Long userId);
    List<Review> findByStatusOrderBySubmittedDateAsc(ReviewStatus status);

    @Query("select avg(r.rating), count(r) from Review r where r.product.id = :productId and r.status = org.example.retail_inventory_managment.enums.ReviewStatus.PUBLISHED")
    Object[] publishedSummary(Long productId);
}
