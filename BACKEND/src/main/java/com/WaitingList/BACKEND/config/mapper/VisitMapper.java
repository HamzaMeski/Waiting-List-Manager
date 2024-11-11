package com.WaitingList.BACKEND.config.mapper;

import com.WaitingList.BACKEND.dto.request.VisitRequestDTO;
import com.WaitingList.BACKEND.dto.response.VisitResponseDTO;
import com.WaitingList.BACKEND.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {VisitorMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitMapper {
    @Mapping(target = "visitor", ignore = true)
    @Mapping(target = "waitingRoom", ignore = true)
    Visit toEntity(VisitRequestDTO dto);

    VisitResponseDTO toDto(Visit entity);
}