package com.WaitingList.BACKEND.service.interfaces;

import com.WaitingList.BACKEND.dto.response.visit.VisitResponseDTO;
import com.WaitingList.BACKEND.dto.response.visitor.VisitorResponseDTO;
import com.WaitingList.BACKEND.dto.request.visitor.VisitorRequestDTO;

public interface VisitorService {
    public VisitorResponseDTO register(VisitorRequestDTO requestDTO);
    public VisitorResponseDTO getById(Long id);
    public VisitResponseDTO getCurrentVisit(Long id);
}
