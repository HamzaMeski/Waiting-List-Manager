package com.WaitingList.BACKEND.controller.v1;

import com.WaitingList.BACKEND.dto.request.WaitingRoomRequestDTO;
import com.WaitingList.BACKEND.dto.response.WaitingRoomResponseDTO;
import com.WaitingList.BACKEND.service.interfaces.WaitingRoomService;
import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}/algorithm")
    public ResponseEntity<WaitingRoomResponseDTO> updateAlgorithm(
            @PathVariable Long id,
            @RequestParam SchedulingAlgorithm algorithm) {
        return ResponseEntity.ok(waitingRoomService.updateAlgorithm(id, algorithm));
    }
}