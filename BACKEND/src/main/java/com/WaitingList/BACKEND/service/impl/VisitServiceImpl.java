package com.WaitingList.BACKEND.service.impl;

import com.WaitingList.BACKEND.dto.request.visit.VisitRequestDTO;
import com.WaitingList.BACKEND.dto.response.visit.VisitResponseDTO;
import com.WaitingList.BACKEND.entity.Visit;
import com.WaitingList.BACKEND.entity.Visitor;
import com.WaitingList.BACKEND.entity.WaitingRoom;
import com.WaitingList.BACKEND.exception.DuplicateResourceException;
import com.WaitingList.BACKEND.exception.ResourceNotFoundException;
import com.WaitingList.BACKEND.exception.ValidationException;
import com.WaitingList.BACKEND.repository.VisitRepository;
import com.WaitingList.BACKEND.repository.VisitorRepository;
import com.WaitingList.BACKEND.repository.WaitingRoomRepository;
import com.WaitingList.BACKEND.service.interfaces.VisitService;
import com.WaitingList.BACKEND.config.mapper.VisitMapper;
import com.WaitingList.BACKEND.util.constants.VisitorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;
    private final WaitingRoomRepository waitingRoomRepository;
    private final VisitorRepository visitorRepository;
    private final VisitMapper visitMapper;

    private boolean isWithinServiceHours(WaitingRoom waitingRoom, LocalDateTime arrivalTime) {
        LocalTime time = arrivalTime.toLocalTime();

        switch (waitingRoom.getServiceTime()) {
            case CONTINUOUS:
                return time.isAfter(LocalTime.of(8, 0)) &&
                        time.isBefore(LocalTime.of(17, 0));
            case MORNING:
                return time.isAfter(LocalTime.of(8, 0)) &&
                        time.isBefore(LocalTime.of(12, 0));
            case AFTERNOON:
                return time.isAfter(LocalTime.of(14, 0)) &&
                        time.isBefore(LocalTime.of(17, 0));
            default:
                return false;
        }
    }

    @Override
    public VisitResponseDTO registerArrival(VisitRequestDTO requestDTO) {
        WaitingRoom waitingRoom = waitingRoomRepository.findById(requestDTO.getWaitingRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Waiting room not found"));

        if(waitingRoom.getVisits().size() >= waitingRoom.getMaxCapacity()) {
            throw new ValidationException("Can not register new arrival, Current Capacity Will over Max Capacity");
        }

        LocalDateTime now = LocalDateTime.now();


        // Validate service hours
//        if (!isWithinServiceHours(waitingRoom, now)) {
//            throw new ValidationException("Visit registration is not allowed during this time");
//        }

        Visitor visitor = visitorRepository.findById(requestDTO.getVisitorId())
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));

        // check if visitor is not already in the same waiting room
        if(visitRepository.existsByVisitorIdAndWaitingRoomId(visitor.getId(), waitingRoom.getId())) {
             throw new DuplicateResourceException("Visitor is already in the same waiting room");
        }

        Visit visit = visitMapper.toEntity(requestDTO);
        visit.setVisitor(visitor);
        visit.setWaitingRoom(waitingRoom);
        visit.setArrivalTime(LocalDateTime.now());
        visit.setStatus(VisitorStatus.WAITING);

        return visitMapper.toResponseDto(visitRepository.save(visit));
    }

    @Override
    public List<VisitResponseDTO> getQueueByAlgorithm(Long waitingRoomId) {
        WaitingRoom waitingRoom = waitingRoomRepository.findById(waitingRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Waiting room not found"));

        List<Visit> visits;
        switch (waitingRoom.getAlgorithm()) {
            case FIFO:
                visits = visitRepository.findByWaitingRoomIdOrderByArrivalTimeAsc(waitingRoomId);
                break;
            case PRIORITY:
                visits = visitRepository.findByWaitingRoomIdOrderByPriorityDescArrivalTimeAsc(waitingRoomId);
                break;
            case SJF:
                visits = visitRepository.findByWaitingRoomIdOrderByEstimatedProcessingTimeAscArrivalTimeAsc(waitingRoomId);
                break;
            default:
                throw new ValidationException("Unknown scheduling algorithm");
        }

        return visits.stream()
                .map(visitMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public VisitResponseDTO startService(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found"));

        if (visit.getStatus() != VisitorStatus.WAITING) {
            throw new ValidationException("Visit is not in WAITING status");
        }

        visit.setStatus(VisitorStatus.IN_PROGRESS);
        visit.setStartTime(LocalDateTime.now());

        return visitMapper.toResponseDto(visitRepository.save(visit));
    }

    @Override
    public VisitResponseDTO completeService(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found"));

        if (visit.getStatus() != VisitorStatus.IN_PROGRESS) {
            throw new ValidationException("Visit is not IN_PROGRESS");
        }

        visit.setStatus(VisitorStatus.FINISHED);
        visit.setEndTime(LocalDateTime.now());

        return visitMapper.toResponseDto(visitRepository.save(visit));
    }

    @Override
    public VisitResponseDTO cancelVisit(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found"));

        if (visit.getStatus() == VisitorStatus.FINISHED) {
            throw new ValidationException("Cannot cancel a finished visit");
        }

        visit.setStatus(VisitorStatus.CANCELLED);
        visit.setEndTime(LocalDateTime.now());

        return visitMapper.toResponseDto(visitRepository.save(visit));
    }
}