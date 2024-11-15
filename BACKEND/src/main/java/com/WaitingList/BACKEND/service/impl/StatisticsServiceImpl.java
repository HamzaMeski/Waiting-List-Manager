package com.WaitingList.BACKEND.service.impl;

import com.WaitingList.BACKEND.dto.response.statistics.WaitingRoomStatisticsDTO;
import com.WaitingList.BACKEND.entity.Visit;
import com.WaitingList.BACKEND.entity.WaitingRoom;
import com.WaitingList.BACKEND.entity.WaitingRoomStatistics;
import com.WaitingList.BACKEND.exception.ResourceNotFoundException;
import com.WaitingList.BACKEND.repository.VisitRepository;
import com.WaitingList.BACKEND.repository.WaitingRoomRepository;
import com.WaitingList.BACKEND.repository.WaitingRoomStatisticsRepository;
import com.WaitingList.BACKEND.service.interfaces.StatisticsService;
import com.WaitingList.BACKEND.util.constants.VisitorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final WaitingRoomRepository waitingRoomRepository;
    private final WaitingRoomStatisticsRepository statisticsRepository;

    @Override
    public WaitingRoomStatisticsDTO getStatistics(Long waitingRoomId) {
        WaitingRoomStatistics statistics = calculateStatistics(waitingRoomId);
        
        return mapToDTO(statistics);
    }

    private WaitingRoomStatistics calculateStatistics(Long waitingRoomId) {
        WaitingRoom waitingRoom = waitingRoomRepository.findById(waitingRoomId)
            .orElseThrow(() -> new ResourceNotFoundException("Waiting room not found"));

        List<Visit> visits = waitingRoom.getVisits().stream().toList();
        
        WaitingRoomStatistics statistics = statisticsRepository
            .findById(waitingRoomId)
            .orElse(new WaitingRoomStatistics());

        statistics.setWaitingRoom(waitingRoom);
        
        // Calculate counts
        statistics.setTotalVisitors(visits.size());
        statistics.setCompletedVisits((int) visits.stream()
            .filter(v -> v.getStatus() == VisitorStatus.FINISHED)
            .count());
        statistics.setCancelledVisits((int) visits.stream()
            .filter(v -> v.getStatus() == VisitorStatus.CANCELLED)
            .count());
        statistics.setInProgressVisits((int) visits.stream()
            .filter(v -> v.getStatus() == VisitorStatus.IN_PROGRESS)
            .count());
        statistics.setWaitingVisits((int) visits.stream()
            .filter(v -> v.getStatus() == VisitorStatus.WAITING)
            .count());

        // Calculate average waiting time
        double avgWaitingTime = visits.stream()
            .filter(v -> v.getStartTime() != null)
            .mapToLong(v -> Duration.between(v.getArrivalTime(), v.getStartTime()).toMinutes())
            .average()
            .orElse(0.0);
        statistics.setAverageWaitingTime(avgWaitingTime);

        // Calculate average processing time
        double avgProcessingTime = visits.stream()
            .filter(v -> v.getEndTime() != null && v.getStartTime() != null)
            .mapToLong(v -> Duration.between(v.getStartTime(), v.getEndTime()).toMinutes())
            .average()
            .orElse(0.0);
        statistics.setAverageProcessingTime(avgProcessingTime);

        // Calculate satisfaction rate (based on waiting time threshold of 30 minutes)
        long satisfiedVisits = visits.stream()
            .filter(v -> v.getStartTime() != null)
            .filter(v -> Duration.between(v.getArrivalTime(), v.getStartTime()).toMinutes() < 30)
            .count();
        statistics.setSatisfactionRate(visits.isEmpty() ? 0.0 : 
            (double) satisfiedVisits / visits.size() * 100);

        return statisticsRepository.save(statistics);
    }

    private WaitingRoomStatisticsDTO mapToDTO(WaitingRoomStatistics statistics) {
        WaitingRoomStatisticsDTO dto = new WaitingRoomStatisticsDTO();
        dto.setWaitingRoomId(statistics.getWaitingRoom().getId());
        dto.setAverageWaitingTime(statistics.getAverageWaitingTime());
        dto.setAverageProcessingTime(statistics.getAverageProcessingTime());
        dto.setTotalVisitors(statistics.getTotalVisitors());
        dto.setCompletedVisits(statistics.getCompletedVisits());
        dto.setCancelledVisits(statistics.getCancelledVisits());
        dto.setInProgressVisits(statistics.getInProgressVisits());
        dto.setWaitingVisits(statistics.getWaitingVisits());
        dto.setSatisfactionRate(statistics.getSatisfactionRate());
        return dto;
    }
} 