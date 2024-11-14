package com.WaitingList.BACKEND.entity;

import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
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

    private Integer maxCapacity;

    @OneToMany(mappedBy = "waitingRoom")
    private Set<Visit> visits = new HashSet<>();

    // opening hours
    @Column(nullable = false)
    private LocalTime openTime = LocalTime.of(9, 0);  // Default 9:00 AM

    @Column(nullable = false)
    private LocalTime closeTime = LocalTime.of(17, 0); // Default 5:00 PM

    @ElementCollection
    @CollectionTable(name = "waiting_room_working_days")
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> workingDays = new HashSet<>(Arrays.asList(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
    ));

    public boolean isOpen() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        return workingDays.contains(now.getDayOfWeek()) &&
                currentTime.isAfter(openTime) &&
                currentTime.isBefore(closeTime);
    }
}
