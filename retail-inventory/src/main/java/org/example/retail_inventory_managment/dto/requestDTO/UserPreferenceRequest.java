package org.example.retail_inventory_managment.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserPreferenceRequest {
    @NotBlank private String preferenceKey;
    @NotBlank private String preferenceValue;
}
