package com.WaitingList.BACKEND.dto.response.waitingRoom;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import com.WaitingList.BACKEND.util.constants.ServiceTime;
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
    private ServiceTime serviceTime;
    private Integer maxCapacity;
    private Integer currentCapacity;
}