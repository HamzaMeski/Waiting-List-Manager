package com.WaitingList.BACKEND.dto.request.visit;

import com.WaitingList.BACKEND.validation.annotation.AlgorithmFieldValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AlgorithmFieldValidation
public class VisitRequestDTO {
    @NotNull(message = "Visitor ID is required")
    private Long visitorId;

    @NotNull(message = "Waiting room ID is required")
    private Long waitingRoomId;

    @Positive(message = "Estimated processing time must be positive")
    private Integer estimatedProcessingTime;

    @Positive(message = "Priority must be positive")
    private Integer priority;
}