package org.example.retail_inventory_managment.dto.resposneDTO;

import lombok.Builder;
import lombok.Value;

@Value @Builder
public class UserPreferenceResponse {
    Long id;
    String preferenceKey;
    String preferenceValue;
}
