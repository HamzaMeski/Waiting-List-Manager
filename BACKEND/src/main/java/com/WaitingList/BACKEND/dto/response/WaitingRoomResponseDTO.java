package com.WaitingList.BACKEND.dto.response;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class WaitingRoomResponseDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private SchedulingAlgorithm algorithm;
    private Integer maxCapacity;
    private Integer currentCapacity;
}