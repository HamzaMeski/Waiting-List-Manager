package com.WaitingList.BACKEND.dto.response;

import com.WaitingList.BACKEND.util.constants.VisitorStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class VisitResponseDTO {
    private Long id;
    private VisitorResponseDTO visitor;
    private LocalDateTime arrivalTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer priority;
    private Integer estimatedProcessingTime;
    private VisitorStatus status;
}