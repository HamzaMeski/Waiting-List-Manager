package com.WaitingList.BACKEND.service.impl;

import com.WaitingList.BACKEND.config.AppConfig;
import com.WaitingList.BACKEND.dto.request.waitingRoom.AlgorithmUpdateDTO;
import com.WaitingList.BACKEND.dto.request.waitingRoom.WaitingRoomRequestDTO;
import com.WaitingList.BACKEND.dto.response.waitingRoom.WaitingRoomResponseDTO;
import com.WaitingList.BACKEND.entity.WaitingRoom;
import com.WaitingList.BACKEND.exception.ResourceNotFoundException;
import com.WaitingList.BACKEND.repository.VisitRepository;
import com.WaitingList.BACKEND.repository.WaitingRoomRepository;
import com.WaitingList.BACKEND.service.interfaces.WaitingRoomService;
import com.WaitingList.BACKEND.config.mapper.WaitingRoomMapper;
import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import com.WaitingList.BACKEND.util.constants.VisitorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WaitingRoomServiceImpl implements WaitingRoomService {
    private final WaitingRoomRepository waitingRoomRepository;
    private final VisitRepository visitRepository;
    private final WaitingRoomMapper waitingRoomMapper;
    private final AppConfig appConfig;

    @Override
    public WaitingRoomResponseDTO create(WaitingRoomRequestDTO requestDTO) {
        log.info("start create...");
        int maxCapacity = requestDTO.getMaxCapacity() != 0 ? requestDTO.getMaxCapacity() : appConfig.getMaxCapacity();

        log.info("appConfig defaultAlgo: {}", appConfig.getDefaultAlgo());

        SchedulingAlgorithm algorithm = null;
        if(requestDTO.getAlgorithm() == null ) {
            if(appConfig.getDefaultAlgo().equals("FIFO")) algorithm = SchedulingAlgorithm.FIFO;
            else if(appConfig.getDefaultAlgo().equals("PRIORITY")) algorithm = SchedulingAlgorithm.PRIORITY;
            else algorithm = SchedulingAlgorithm.SJF;
        }else {
            algorithm = requestDTO.getAlgorithm();
        }
        log.info("maxCapacity: {}", maxCapacity);
        log.info("algo: {}", algorithm);
        WaitingRoom waitingRoom = waitingRoomMapper.toEntity(requestDTO);
        waitingRoom.setMaxCapacity(maxCapacity);
        waitingRoom.setAlgorithm(algorithm);

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

        // Check for active visits (those not COMPLETED or CANCELLED)
        boolean hasActiveVisits = visitRepository.existsByWaitingRoomIdAndStatusIn(
                id,
                Set.of(VisitorStatus.WAITING, VisitorStatus.IN_PROGRESS)
        );
        if (hasActiveVisits) {
            throw new IllegalStateException(
                    "Cannot change algorithm while there are active visits. Complete or cancel existing visits first."
            );
        }

        waitingRoom.setAlgorithm(algorithmUpdateDTO.getAlgorithm());
        return waitingRoomMapper.toResponseDto(waitingRoomRepository.save(waitingRoom));
    }
}