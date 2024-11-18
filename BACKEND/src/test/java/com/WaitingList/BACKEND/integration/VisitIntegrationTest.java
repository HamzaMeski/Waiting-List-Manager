package com.WaitingList.BACKEND.integration;

import com.WaitingList.BACKEND.entity.Visitor;
import com.WaitingList.BACKEND.entity.Visit;
import com.WaitingList.BACKEND.entity.WaitingRoom;
import com.WaitingList.BACKEND.repository.VisitorRepository;
import com.WaitingList.BACKEND.repository.VisitRepository;
import com.WaitingList.BACKEND.repository.WaitingRoomRepository;
import com.WaitingList.BACKEND.service.interfaces.VisitService;
import com.WaitingList.BACKEND.util.constants.VisitorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimpleVisitIntegrationTest {

    @Autowired
    private VisitService visitService;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private WaitingRoomRepository waitingRoomRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    private WaitingRoom waitingRoom;
    private Visitor visitor;

    @BeforeEach
    void setUp() {
        // Clean up database
        visitRepository.deleteAll();
        waitingRoomRepository.deleteAll();
        visitorRepository.deleteAll();

        // Create test waiting room
        waitingRoom = new WaitingRoom();
        waitingRoom.setName("Test Room");
        waitingRoom.setDate(LocalDate.now());
        waitingRoom = waitingRoomRepository.save(waitingRoom);

        // Create test visitor
        visitor = new Visitor();
        visitor.setName("Test Visitor");
        visitor = visitorRepository.save(visitor);
    }

    @Test
    void whenCancelVisit_ThenStatusShouldBeCancelled() {
        // Arrange
        Visit visit = new Visit();
        visit.setWaitingRoom(waitingRoom);
        visit.setVisitor(visitor);
        visit.setStatus(VisitorStatus.WAITING);
        visit.setArrivalTime(LocalDateTime.now());
        Visit savedVisit = visitRepository.save(visit);

        // Act
        visitService.cancelVisit(savedVisit.getId());

        // Assert
        Visit cancelledVisit = visitRepository.findById(savedVisit.getId()).orElseThrow();
        assertEquals(VisitorStatus.CANCELLED, cancelledVisit.getStatus());
    }

    @Test
    void whenStartService_ThenStatusShouldBeInProgress() {
        // Arrange
        Visit visit = new Visit();
        visit.setWaitingRoom(waitingRoom);
        visit.setVisitor(visitor);
        visit.setStatus(VisitorStatus.WAITING);
        visit.setArrivalTime(LocalDateTime.now());
        Visit savedVisit = visitRepository.save(visit);

        // Act
        visitService.startService(savedVisit.getId());

        // Assert
        Visit startedVisit = visitRepository.findById(savedVisit.getId()).orElseThrow();
        assertEquals(VisitorStatus.IN_PROGRESS, startedVisit.getStatus());
        assertNotNull(startedVisit.getStartTime());
    }
}