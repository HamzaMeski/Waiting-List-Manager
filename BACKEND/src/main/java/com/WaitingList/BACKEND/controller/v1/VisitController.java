package com.WaitingList.BACKEND.controller.v1;

import com.WaitingList.BACKEND.dto.request.VisitRequestDTO;
import com.WaitingList.BACKEND.dto.response.VisitResponseDTO;
import com.WaitingList.BACKEND.service.VisitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/visits")
public class VisitController {
    private final VisitService visitService;

    @PostMapping("/arrival")
    public ResponseEntity<VisitResponseDTO> registerArrival(@RequestBody VisitRequestDTO requestDTO) {
        return ResponseEntity.ok(visitService.registerArrival(requestDTO));
    }

    @GetMapping("/waiting-room/{waitingRoomId}/queue")
    public ResponseEntity<List<VisitResponseDTO>> getQueue(@PathVariable Long waitingRoomId) {
        return ResponseEntity.ok(visitService.getQueueByAlgorithm(waitingRoomId));
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<VisitResponseDTO> startService(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.startService(id));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<VisitResponseDTO> completeService(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.completeService(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<VisitResponseDTO> cancelVisit(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.cancelVisit(id));
    }
}