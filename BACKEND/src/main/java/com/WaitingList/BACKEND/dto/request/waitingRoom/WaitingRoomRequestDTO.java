package com.WaitingList.BACKEND.dto.request.waitingRoom;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import com.WaitingList.BACKEND.util.constants.ServiceTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class WaitingRoomRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull
    private LocalDate date;

    private SchedulingAlgorithm algorithm;

    private ServiceTime serviceTime = ServiceTime.CONTINUOUS;

 //   @Positive(message = "Max capacity must be positive")
    private int maxCapacity;
}