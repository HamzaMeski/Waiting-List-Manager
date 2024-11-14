package com.WaitingList.BACKEND.dto.request.waitingRoom;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
public class BusinessHoursUpdateDTO {
    @NotNull(message = "Opening time is required")
    private LocalTime openTime;

    @NotNull(message = "Closing time is required")
    private LocalTime closeTime;

    @NotNull(message = "Working days are required")
    private Set<DayOfWeek> workingDays;
} 