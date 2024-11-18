package com.WaitingList.BACKEND.entity;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;

import com.WaitingList.BACKEND.util.constants.ServiceTime;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

@Entity
@Table(name = "waiting_rooms")
@Getter
@Setter
public class WaitingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchedulingAlgorithm algorithm = SchedulingAlgorithm.FIFO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceTime serviceTime = ServiceTime.CONTINUOUS;

    private Integer maxCapacity;

    @OneToMany(mappedBy = "waitingRoom")
    private Set<Visit> visits = new HashSet<>();
}
