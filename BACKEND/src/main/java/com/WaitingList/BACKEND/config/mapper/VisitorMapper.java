package com.WaitingList.BACKEND.config.mapper;

import com.WaitingList.BACKEND.dto.request.VisitorRequestDTO;
import com.WaitingList.BACKEND.dto.response.VisitorResponseDTO;
import com.WaitingList.BACKEND.entity.Visitor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitorMapper {
    Visitor toEntity(VisitorRequestDTO dto);
    VisitorResponseDTO toDto(Visitor entity);
}