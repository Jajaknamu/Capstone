package com.capstone.professor.dto;

import com.capstone.professor.entity.ProfessorEducationEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfessorEducationDTO {
    private  Long id;
    private String education;

    //entity -> dto
    public static ProfessorEducationDTO toProfessorEducationDTO(ProfessorEducationEntity educationEntity) {
        ProfessorEducationDTO educationDTO = new ProfessorEducationDTO();
        educationDTO.setId(educationEntity.getId());
        educationDTO.setEducation(educationEntity.getEducation());
        return educationDTO;
    }
}
