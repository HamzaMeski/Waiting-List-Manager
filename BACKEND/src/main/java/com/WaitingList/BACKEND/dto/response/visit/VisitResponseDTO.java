package com.WaitingList.BACKEND.dto.response.visit;

import com.WaitingList.BACKEND.dto.response.visitor.VisitorResponseDTO;
import com.WaitingList.BACKEND.dto.response.waitingRoom.WaitingRoomResponseDTO;
import com.WaitingList.BACKEND.util.constants.VisitorStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class VisitResponseDTO {
    private Long id;
    private VisitorResponseDTO visitor;
    private WaitingRoomResponseDTO waitingRoom;
    private LocalDateTime arrivalTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer priority;
    private Integer estimatedProcessingTime;
    private VisitorStatus status;
}