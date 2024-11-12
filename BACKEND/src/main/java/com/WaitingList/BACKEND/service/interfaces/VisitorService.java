package com.WaitingList.BACKEND.service.interfaces;

import com.WaitingList.BACKEND.dto.response.VisitResponseDTO;
import com.WaitingList.BACKEND.dto.response.VisitorResponseDTO;
import com.WaitingList.BACKEND.dto.request.VisitorRequestDTO;

public interface VisitorService {
    public VisitorResponseDTO register(VisitorRequestDTO requestDTO);
    public VisitorResponseDTO getById(Long id);
    public VisitResponseDTO getCurrentVisit(Long id);
}
