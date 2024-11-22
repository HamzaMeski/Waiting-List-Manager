package com.WaitingList.BACKEND.entity;

import com.WaitingList.BACKEND.util.constants.VisitorStatus;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@Setter
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiting_room_id", nullable = false)
    private WaitingRoom waitingRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer priority;

    @Column(name = "estimated_processing_time")
    private Integer estimatedProcessingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisitorStatus status = VisitorStatus.WAITING;
}