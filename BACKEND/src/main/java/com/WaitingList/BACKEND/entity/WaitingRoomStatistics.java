package com.WaitingList.BACKEND.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "waiting_room_statistics")
@Getter
@Setter
public class WaitingRoomStatistics {
    @Id
    private Long id; // Will use the same ID as the waiting room

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Uses the same ID as the waiting room
    @JoinColumn(name = "waiting_room_id")
    private WaitingRoom waitingRoom;

    private Double averageWaitingTime; // in minutes
    private Double averageProcessingTime; // in minutes

    private Integer totalVisitors;
    private Integer completedVisits;
    private Integer cancelledVisits;
    private Integer inProgressVisits;
    private Integer waitingVisits;

    @Column(name = "satisfaction_rate")
    private Double satisfactionRate;
}