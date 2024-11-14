package com.WaitingList.BACKEND.service.interfaces;

import com.WaitingList.BACKEND.dto.request.waitingRoom.AlgorithmUpdateDTO;
import com.WaitingList.BACKEND.dto.request.waitingRoom.BusinessHoursUpdateDTO;
import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import com.WaitingList.BACKEND.dto.request.waitingRoom.WaitingRoomRequestDTO;
import com.WaitingList.BACKEND.dto.response.waitingRoom.WaitingRoomResponseDTO;

import java.util.List;

public interface WaitingRoomService {
    public WaitingRoomResponseDTO create(WaitingRoomRequestDTO requestDTO);
    public WaitingRoomResponseDTO getById(Long id);
    public List<WaitingRoomResponseDTO> getAll();
    public WaitingRoomResponseDTO getOrCreateTodayWaitingRoom();
    public WaitingRoomResponseDTO updateAlgorithm(Long id, AlgorithmUpdateDTO algorithmUpdateDTO);
    public WaitingRoomResponseDTO updateBusinessHours(Long id, BusinessHoursUpdateDTO updateDTO);
}