package com.capstone.professor.dto;

import com.capstone.professor.entity.ProfessorCareersEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfessorCareersDTO {

    private Long id;
    private String description;

    //entity -> dto
    public static ProfessorCareersDTO toProfessorCareersDTO(ProfessorCareersEntity careerEntity) {
        ProfessorCareersDTO careersDTO = new ProfessorCareersDTO();
        careersDTO.setId(careerEntity.getId());
        careersDTO.setDescription(careerEntity.getDescription());
        return careersDTO;
    }
}
