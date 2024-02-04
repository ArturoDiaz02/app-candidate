package com.seek.appcandidate.infrastructure.mapper;

import com.seek.appcandidate.domain.model.Candidate;
import com.seek.appcandidate.infrastructure.dto.CreateCandidateDTO;
import com.seek.appcandidate.infrastructure.dto.OutCandidateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICandidateMapper {

    @Mapping(target = "date_of_birth", source = "date_of_birth", ignore = true)
    Candidate candidateFromCreateCandidateDTO(CreateCandidateDTO createCandidateDTO);

    @Mapping(target = "created_at", source = "created_at", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updated_at", source = "updated_at", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "date_of_birth", source = "date_of_birth", dateFormat = "dd-MM-yyyy")
    OutCandidateDTO outCandidateDTOFromCandidate(Candidate candidate);
}
