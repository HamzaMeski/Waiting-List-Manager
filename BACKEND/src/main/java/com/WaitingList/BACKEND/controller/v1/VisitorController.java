package com.WaitingList.BACKEND.controller.v1;

import com.WaitingList.BACKEND.dto.request.visitor.VisitorRequestDTO;
import com.WaitingList.BACKEND.dto.response.visitor.VisitorResponseDTO;
import com.WaitingList.BACKEND.dto.response.visit.VisitResponseDTO;
import com.WaitingList.BACKEND.service.interfaces.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visitors")
@RequiredArgsConstructor
public class VisitorController {
    private final VisitorService visitorService;

    @PostMapping("/register")
    public ResponseEntity<VisitorResponseDTO> register(@RequestBody VisitorRequestDTO requestDTO) {
        return ResponseEntity.ok(visitorService.register(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitorResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<VisitorResponseDTO>> getAll() {
        return ResponseEntity.ok(visitorService.getAll());
    }

    @GetMapping("/{id}/current-visit")
    public ResponseEntity<VisitResponseDTO> getCurrentVisit(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.getCurrentVisit(id));
    }
}