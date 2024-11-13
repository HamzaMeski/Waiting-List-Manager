package com.WaitingList.BACKEND.service.impl;

import com.WaitingList.BACKEND.dto.request.waitingRoom.AlgorithmUpdateDTO;
import com.WaitingList.BACKEND.dto.request.waitingRoom.WaitingRoomRequestDTO;
import com.WaitingList.BACKEND.dto.response.waitingRoom.WaitingRoomResponseDTO;
import com.WaitingList.BACKEND.entity.WaitingRoom;
import com.WaitingList.BACKEND.exception.ResourceNotFoundException;
import com.WaitingList.BACKEND.repository.WaitingRoomRepository;
import com.WaitingList.BACKEND.service.interfaces.WaitingRoomService;
import com.WaitingList.BACKEND.config.mapper.WaitingRoomMapper;
import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.NotActiveException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WaitingRoomServiceImpl implements WaitingRoomService {
    private final WaitingRoomRepository waitingRoomRepository;
    private final WaitingRoomMapper waitingRoomMapper;

    @Override
    public WaitingRoomResponseDTO create(WaitingRoomRequestDTO requestDTO) {
        WaitingRoom waitingRoom = waitingRoomMapper.toEntity(requestDTO);
        return waitingRoomMapper.toResponseDto(waitingRoomRepository.save(waitingRoom));
    }

    @Override
    public WaitingRoomResponseDTO getById(Long id) {
        WaitingRoom waitingRoom = waitingRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Waiting room not found with ID "+ id));

        return waitingRoomMapper.toResponseDto(waitingRoom);
    }

    @Override
    public List<WaitingRoomResponseDTO> getAll() {
        return waitingRoomRepository.findAll().stream()
                .map(waitingRoomMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public WaitingRoomResponseDTO getOrCreateTodayWaitingRoom() {
        LocalDate today = LocalDate.now();
        return waitingRoomRepository.findByDate(today)
                .map(waitingRoomMapper::toResponseDto)
                .orElseGet(() -> {
                    WaitingRoomRequestDTO requestDTO = new WaitingRoomRequestDTO();
                    requestDTO.setDate(today);
                    return create(requestDTO);
                });
    }

    @Override
    public WaitingRoomResponseDTO updateAlgorithm(Long id, AlgorithmUpdateDTO algorithmUpdateDTO) {
        WaitingRoom waitingRoom = waitingRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Waiting room not found"));

        waitingRoom.setAlgorithm(algorithmUpdateDTO.getAlgorithm());
        return waitingRoomMapper.toResponseDto(waitingRoomRepository.save(waitingRoom));
    }
}