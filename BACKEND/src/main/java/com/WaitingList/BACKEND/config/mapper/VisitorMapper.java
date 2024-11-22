package com.WaitingList.BACKEND.config.mapper;

import com.WaitingList.BACKEND.dto.request.visitor.VisitorRequestDTO;
import com.WaitingList.BACKEND.dto.response.visitor.VisitorResponseDTO;
import com.WaitingList.BACKEND.entity.Visitor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitorMapper {
    Visitor toEntity(VisitorRequestDTO requestDTO);

    VisitorResponseDTO toResponseDto(Visitor entity);

    void updateEntity(@MappingTarget Visitor visitor, VisitorRequestDTO requestDTO);
}