package com.WaitingList.BACKEND.controller.v1;

import com.WaitingList.BACKEND.dto.request.visit.VisitRequestDTO;
import com.WaitingList.BACKEND.dto.response.visit.VisitResponseDTO;
import com.WaitingList.BACKEND.service.interfaces.VisitService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/v1/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/arrival")
    public ResponseEntity<VisitResponseDTO> registerArrival(@RequestBody @Valid VisitRequestDTO requestDTO) {
        System.out.println("arrival...");
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