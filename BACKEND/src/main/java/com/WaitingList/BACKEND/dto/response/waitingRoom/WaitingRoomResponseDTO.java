package com.WaitingList.BACKEND.dto.response.waitingRoom;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import lombok.Getter;
import lombok.Setter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
public class WaitingRoomResponseDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private SchedulingAlgorithm algorithm;
    private Integer maxCapacity;
    private Integer currentCapacity;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Set<DayOfWeek> workingDays;
}