package com.cgi.sophos.engagement;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EngagementMapper {
    EngagementDTO toDTO(Engagement engagement);
    List<EngagementDTO> toDTO(List<Engagement> engagements);
    Engagement toEntity(EngagementDTO dto);
    List<Engagement> toEntities(List<EngagementDTO> dtoList);
}
