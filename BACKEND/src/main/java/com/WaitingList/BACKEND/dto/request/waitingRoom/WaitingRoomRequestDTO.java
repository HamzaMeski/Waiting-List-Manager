package com.WaitingList.BACKEND.dto.request.waitingRoom;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
public class WaitingRoomRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private SchedulingAlgorithm algorithm = SchedulingAlgorithm.FIFO;

    @Positive(message = "Max capacity must be positive")
    private Integer maxCapacity;

    // Default to 9 AM if not specified
    private LocalTime openTime = LocalTime.of(9, 0);

    // Default to 5 PM if not specified
    private LocalTime closeTime = LocalTime.of(17, 0);

    // Default to Monday-Friday if not specified
    private Set<DayOfWeek> workingDays = Set.of(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
    );
}