package com.WaitingList.BACKEND.service.impl;

import com.WaitingList.BACKEND.dto.request.visitor.VisitorRequestDTO;
import com.WaitingList.BACKEND.dto.response.visitor.VisitorResponseDTO;
import com.WaitingList.BACKEND.dto.response.visit.VisitResponseDTO;
import com.WaitingList.BACKEND.entity.Visitor;
import com.WaitingList.BACKEND.exception.ResourceNotFoundException;
import com.WaitingList.BACKEND.repository.VisitRepository;
import com.WaitingList.BACKEND.repository.VisitorRepository;
import com.WaitingList.BACKEND.service.interfaces.VisitorService;
import com.WaitingList.BACKEND.config.mapper.VisitorMapper;
import com.WaitingList.BACKEND.config.mapper.VisitMapper;
import com.WaitingList.BACKEND.util.constants.VisitorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {
    private final VisitorRepository visitorRepository;
    private final VisitRepository visitRepository;
    private final VisitorMapper visitorMapper;
    private final VisitMapper visitMapper;

    @Override
    public VisitorResponseDTO register(VisitorRequestDTO requestDTO) {
        Visitor visitor = visitorMapper.toEntity(requestDTO);
        return visitorMapper.toResponseDto(visitorRepository.save(visitor));
    }

    @Override
    public VisitorResponseDTO getById(Long id) {
        return visitorRepository.findById(id)
                .map(visitorMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));
    }

    @Override
    public List<VisitorResponseDTO> getAll() {
        return visitorRepository.findAll().stream()
                .map(visitorMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public VisitResponseDTO getCurrentVisit(Long id) {
        return visitRepository.findByVisitorIdAndStatusIn(
                        id,
                        Arrays.asList(VisitorStatus.WAITING, VisitorStatus.IN_PROGRESS))
                .map(visitMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("No active visit found for visitor"));
    }
}