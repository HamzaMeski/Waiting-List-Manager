package com.WaitingList.BACKEND.integration;

import com.WaitingList.BACKEND.dto.request.visit.VisitRequestDTO;
import com.WaitingList.BACKEND.dto.response.visit.VisitResponseDTO;
import com.WaitingList.BACKEND.entity.Visitor;
import com.WaitingList.BACKEND.entity.WaitingRoom;
import com.WaitingList.BACKEND.repository.VisitRepository;
import com.WaitingList.BACKEND.repository.VisitorRepository;
import com.WaitingList.BACKEND.repository.WaitingRoomRepository;
import com.WaitingList.BACKEND.service.interfaces.VisitService;
import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class VisitIntegrationTest {

    @Autowired
    private VisitService visitService;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private WaitingRoomRepository waitingRoomRepository;

    @Autowired
    private VisitRepository visitRepository;

    private Visitor visitor;
    private WaitingRoom waitingRoom;

    @BeforeEach
    void setUp() {
        // Create test visitor
        visitor = new Visitor();
        visitor.setName("John Doe");
        visitor = visitorRepository.save(visitor);

        // Create test waiting room with all required fields
        waitingRoom = new WaitingRoom();
        waitingRoom.setName("Test Room");
        waitingRoom.setMaxCapacity(10);
        waitingRoom.setDate(LocalDate.now());
        waitingRoom.setAlgorithm(SchedulingAlgorithm.FIFO); // Set the algorithm
        waitingRoom = waitingRoomRepository.save(waitingRoom);
    }

    @Test
    void registerVisit_Success() {
        // Arrange
        VisitRequestDTO requestDTO = new VisitRequestDTO();
        requestDTO.setVisitorId(visitor.getId());
        requestDTO.setWaitingRoomId(waitingRoom.getId());

        // Act
        VisitResponseDTO response = visitService.registerArrival(requestDTO);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(visitor.getId(), response.getVisitor().getId());
        assertEquals(waitingRoom.getId(), response.getWaitingRoom().getId());

        // Verify in database
        assertTrue(visitRepository.existsByVisitorIdAndWaitingRoomId(
                visitor.getId(),
                waitingRoom.getId()
        ));
    }
}