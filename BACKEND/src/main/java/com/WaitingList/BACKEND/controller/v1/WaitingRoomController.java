package com.WaitingList.BACKEND.controller.v1;

import com.WaitingList.BACKEND.dto.request.waitingRoom.AlgorithmUpdateDTO;
import com.WaitingList.BACKEND.dto.request.waitingRoom.WaitingRoomRequestDTO;
import com.WaitingList.BACKEND.dto.response.waitingRoom.WaitingRoomResponseDTO;
import com.WaitingList.BACKEND.service.interfaces.WaitingRoomService;
import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/waiting-rooms")
@RequiredArgsConstructor
public class WaitingRoomController {
    private final WaitingRoomService waitingRoomService;

    @PostMapping
    public ResponseEntity<WaitingRoomResponseDTO> create(@RequestBody WaitingRoomRequestDTO requestDTO) {
        return ResponseEntity.ok(waitingRoomService.create(requestDTO));
    }

    @GetMapping("/today")
    public ResponseEntity<WaitingRoomResponseDTO> getTodayWaitingRoom() {
        return ResponseEntity.ok(waitingRoomService.getOrCreateTodayWaitingRoom());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaitingRoomResponseDTO> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(waitingRoomService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<WaitingRoomResponseDTO>> getAll() {
        return ResponseEntity.ok(waitingRoomService.getAll());
    }

    @PatchMapping("/{id}/algorithm")
    public ResponseEntity<WaitingRoomResponseDTO> updateAlgorithm(
            @PathVariable Long id,
            @RequestBody AlgorithmUpdateDTO algorithmUpdateDTO
    ) {
        return ResponseEntity.ok(waitingRoomService.updateAlgorithm(id, algorithmUpdateDTO));
    }
}