package com.WaitingList.BACKEND.service.interfaces;

import com.WaitingList.BACKEND.dto.request.visit.VisitRequestDTO;
import com.WaitingList.BACKEND.dto.response.visit.VisitResponseDTO;

import java.util.List;

public interface VisitService {
    public VisitResponseDTO registerArrival(VisitRequestDTO requestDTO);
    public List<VisitResponseDTO> getQueueByAlgorithm(Long waitingRoomId);
    public VisitResponseDTO startService(Long id);
    public VisitResponseDTO completeService(Long id);
    public VisitResponseDTO cancelVisit(Long id);
}
