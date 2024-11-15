package com.WaitingList.BACKEND.controller.v1;

import com.WaitingList.BACKEND.dto.response.statistics.WaitingRoomStatisticsDTO;
import com.WaitingList.BACKEND.service.interfaces.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/waiting-room/{waitingRoomId}")
    public ResponseEntity<WaitingRoomStatisticsDTO> getStatistics(@PathVariable Long waitingRoomId) {
        return ResponseEntity.ok(statisticsService.getStatistics(waitingRoomId));
    }
} 