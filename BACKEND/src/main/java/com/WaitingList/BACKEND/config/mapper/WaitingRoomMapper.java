package com.WaitingList.BACKEND.config.mapper;

import com.WaitingList.BACKEND.dto.request.WaitingRoomRequestDTO;
import com.WaitingList.BACKEND.dto.response.WaitingRoomResponseDTO;
import com.WaitingList.BACKEND.entity.WaitingRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WaitingRoomMapper {
    WaitingRoom toEntity(WaitingRoomRequestDTO dto);

    @Mapping(target = "currentCapacity", expression = "java(entity.getVisits().size())")
    WaitingRoomResponseDTO toDto(WaitingRoom entity);
}