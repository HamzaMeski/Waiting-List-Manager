package com.WaitingList.BACKEND.service.impl;

import com.WaitingList.BACKEND.config.mapper.VisitMapper;
import com.WaitingList.BACKEND.dto.response.visit.VisitResponseDTO;
import com.WaitingList.BACKEND.entity.Visit;
import com.WaitingList.BACKEND.exception.ResourceNotFoundException;
import com.WaitingList.BACKEND.exception.ValidationException;
import com.WaitingList.BACKEND.repository.VisitRepository;
import com.WaitingList.BACKEND.repository.VisitorRepository;
import com.WaitingList.BACKEND.repository.WaitingRoomRepository;
import com.WaitingList.BACKEND.util.constants.VisitorStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceImplTest {
    @Mock
    private VisitRepository visitRepository;
    @Mock
    private WaitingRoomRepository waitingRoomRepository;
    @Mock
    private VisitorRepository visitorRepository;
    @Mock
    private VisitMapper visitMapper;

    @InjectMocks
    private VisitServiceImpl visitService;

    @Test
    void startService_Success_ShouldUpdateVisitStatus() {
        // Arrange
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setStatus(VisitorStatus.WAITING);

        VisitResponseDTO expectedResponse = new VisitResponseDTO();

        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        when(visitMapper.toResponseDto(any())).thenReturn(expectedResponse);

        // Act
        VisitResponseDTO result = visitService.startService(1L);

        // Assert
        assertEquals(VisitorStatus.IN_PROGRESS, visit.getStatus());
        assertNotNull(visit.getStartTime());
        verify(visitRepository).save(visit);
    }

    @Test
    void startService_WhenVisitNotFound_ShouldThrowException() {
        // Arrange
        when(visitRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> visitService.startService(1L));
    }

    @Test
    void startService_WhenVisitNotInWaitingStatus_ShouldThrowException() {
        // Arrange
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setStatus(VisitorStatus.IN_PROGRESS);

        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () ->
                visitService.startService(1L)
        );
        assertEquals("Visit is not in WAITING status", exception.getMessage());
    }

    @Test
    void completeService_Success_ShouldUpdateVisitStatus() {
        // Arrange
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setStatus(VisitorStatus.IN_PROGRESS);
        visit.setStartTime(LocalDateTime.now().minusMinutes(30));

        VisitResponseDTO expectedResponse = new VisitResponseDTO();

        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        when(visitMapper.toResponseDto(any())).thenReturn(expectedResponse);

        // Act
        VisitResponseDTO result = visitService.completeService(1L);

        // Assert
        assertEquals(VisitorStatus.FINISHED, visit.getStatus());
        assertNotNull(visit.getEndTime());
        verify(visitRepository).save(visit);
    }

    @Test
    void completeService_WhenVisitNotFound_ShouldThrowException() {
        // Arrange
        when(visitRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> visitService.completeService(1L));
    }

    @Test
    void completeService_WhenVisitNotInProgress_ShouldThrowException() {
        // Arrange
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setStatus(VisitorStatus.WAITING);

        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () ->
                visitService.completeService(1L)
        );
        assertEquals("Visit is not IN_PROGRESS", exception.getMessage());
    }

    @Test
    void cancelVisit_Success_ShouldUpdateVisitStatus() {
        // Arrange
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setStatus(VisitorStatus.WAITING);

        VisitResponseDTO expectedResponse = new VisitResponseDTO();

        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        when(visitMapper.toResponseDto(any())).thenReturn(expectedResponse);

        // Act
        VisitResponseDTO result = visitService.cancelVisit(1L);

        // Assert
        assertEquals(VisitorStatus.CANCELLED, visit.getStatus());
        assertNotNull(visit.getEndTime());
        verify(visitRepository).save(visit);
    }

    @Test
    void cancelVisit_WhenVisitNotFound_ShouldThrowException() {
        // Arrange
        when(visitRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> visitService.cancelVisit(1L));
    }

    @Test
    void cancelVisit_WhenVisitAlreadyFinished_ShouldThrowException() {
        // Arrange
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setStatus(VisitorStatus.FINISHED);

        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () ->
                visitService.cancelVisit(1L)
        );
        assertEquals("Cannot cancel a finished visit", exception.getMessage());
    }
}

/*
=>startService:
    Success case
    Visit not found
    Visit not in waiting status

=>completeService:
    Success case
    Visit not found
    Visit not in progress

=>cancelVisit:
    Success case
    Visit not found
    Visit already finished

=>Each method is tested for:
    Happy path (successful execution)
    Error paths (various failure scenarios)
    Edge cases
*/
