package com.WaitingList.BACKEND.service.interfaces;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import com.WaitingList.BACKEND.dto.request.WaitingRoomRequestDTO;
import com.WaitingList.BACKEND.dto.response.WaitingRoomResponseDTO;

public interface WaitingRoomService {
    public WaitingRoomResponseDTO create(WaitingRoomRequestDTO requestDTO);
    public WaitingRoomResponseDTO getOrCreateTodayWaitingRoom();
    public WaitingRoomResponseDTO updateAlgorithm(Long id, SchedulingAlgorithm algorithm);
}