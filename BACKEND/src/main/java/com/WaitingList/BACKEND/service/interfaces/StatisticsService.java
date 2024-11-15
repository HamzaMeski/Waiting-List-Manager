package com.WaitingList.BACKEND.service.interfaces;

import com.WaitingList.BACKEND.dto.response.statistics.WaitingRoomStatisticsDTO;

public interface StatisticsService {
    WaitingRoomStatisticsDTO getStatistics(Long waitingRoomId);
}