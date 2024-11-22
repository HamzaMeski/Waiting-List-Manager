package com.WaitingList.BACKEND.dto.response.statistics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WaitingRoomStatisticsDTO {
    private Long waitingRoomId;
    
    // Time metrics (in minutes)
    private Double averageWaitingTime;
    private Double averageProcessingTime;
    
    // Visit counts
    private Integer totalVisitors;
    private Integer completedVisits;
    private Integer cancelledVisits;
    private Integer inProgressVisits;
    private Integer waitingVisits;
    
    // Performance metrics
    private Double satisfactionRate; // percentage (0-100)
    
    // You might want to add some derived getters for additional insights
    public Double getCompletionRate() {
        return totalVisitors == 0 ? 0.0 : 
            (double) completedVisits / totalVisitors * 100;
    }
    
    public Double getCancellationRate() {
        return totalVisitors == 0 ? 0.0 : 
            (double) cancelledVisits / totalVisitors * 100;
    }
} 