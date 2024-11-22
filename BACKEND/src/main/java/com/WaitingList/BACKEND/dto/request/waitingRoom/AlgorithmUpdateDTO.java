package com.WaitingList.BACKEND.dto.request.waitingRoom;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AlgorithmUpdateDTO {

    @NotNull(message = "Algorithm is required")
    private SchedulingAlgorithm algorithm;
}