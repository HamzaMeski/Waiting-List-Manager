package com.WaitingList.BACKEND.dto.request.waitingRoom;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import lombok.Getter;

@Getter
public class AlgorithmUpdateDTO {
    private SchedulingAlgorithm algorithm;
}