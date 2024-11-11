package com.WaitingList.BACKEND.dto.request;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class WaitingRoomRequestDTO {
    @NotNull(message = "Date is required")
    private LocalDate date;

    private SchedulingAlgorithm algorithm = SchedulingAlgorithm.FIFO;

    @Positive(message = "Max capacity must be positive")
    private Integer maxCapacity;
}