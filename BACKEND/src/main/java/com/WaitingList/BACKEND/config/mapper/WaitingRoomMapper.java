package com.WaitingList.BACKEND.config.mapper;

import com.WaitingList.BACKEND.dto.request.waitingRoom.WaitingRoomRequestDTO;
import com.WaitingList.BACKEND.dto.response.waitingRoom.WaitingRoomResponseDTO;
import com.WaitingList.BACKEND.entity.WaitingRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WaitingRoomMapper {
    WaitingRoom toEntity(WaitingRoomRequestDTO requestDTO);

    @Mapping(target = "currentCapacity", expression = "java(entity.getVisits().size())")
    @Mapping(target = "openTime", source = "openTime")
    @Mapping(target = "closeTime", source = "closeTime")
    @Mapping(target = "workingDays", source = "workingDays")
    WaitingRoomResponseDTO toResponseDto(WaitingRoom entity);

    void updateEntity(@MappingTarget WaitingRoom waitingRoom, WaitingRoomRequestDTO requestDTO);
}