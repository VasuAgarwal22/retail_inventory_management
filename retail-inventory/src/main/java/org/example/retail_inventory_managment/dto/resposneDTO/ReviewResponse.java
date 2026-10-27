package org.example.retail_inventory_managment.dto.resposneDTO;

import lombok.Builder;
import lombok.Value;
import org.example.retail_inventory_managment.enums.ReviewStatus;

import java.time.Instant;

@Value @Builder
public class ReviewResponse {
    Long id;
    Long productId;
    String productName;
    Long userId;
    String userName;
    Integer rating;
    String reviewText;
    Instant submittedDate;
    Instant moderatedDate;
    ReviewStatus status;
}
