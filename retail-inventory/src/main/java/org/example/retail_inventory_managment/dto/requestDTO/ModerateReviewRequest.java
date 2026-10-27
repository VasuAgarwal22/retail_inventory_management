package org.example.retail_inventory_managment.dto.requestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.retail_inventory_managment.enums.ModerationAction;

@Data
public class ModerateReviewRequest {
    @NotNull private ModerationAction action;
    private String comments;
}
