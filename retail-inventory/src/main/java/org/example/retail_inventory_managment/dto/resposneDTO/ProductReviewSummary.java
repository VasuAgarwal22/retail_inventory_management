package org.example.retail_inventory_managment.dto.resposneDTO;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value @Builder
public class ProductReviewSummary {
    BigDecimal averageRating;
    long reviewCount;
}
