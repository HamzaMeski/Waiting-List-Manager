package com.WaitingList.BACKEND.config.mapper;

import com.WaitingList.BACKEND.dto.request.visit.VisitRequestDTO;
import com.WaitingList.BACKEND.dto.response.visit.VisitResponseDTO;
import com.WaitingList.BACKEND.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {VisitorMapper.class, WaitingRoomMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitMapper {
    Visit toEntity(VisitRequestDTO requestDTO);

    VisitResponseDTO toResponseDto(Visit entity);

    void updateEntity(@MappingTarget Visit visit, VisitRequestDTO requestDTO);
}