package com.capstone.professor.dto;

import com.capstone.professor.entity.ProfessorPublicationsEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfessorPublicationsDTO {

    private  Long id;
    private String title;
    private String contents;

    public static ProfessorPublicationsDTO toProfessorPublicationsDTO(ProfessorPublicationsEntity publicationEntity) {
        ProfessorPublicationsDTO publicationsDTO = new ProfessorPublicationsDTO();
        publicationsDTO.setId(publicationEntity.getId());
        publicationsDTO.setTitle(publicationEntity.getTitle());
        publicationsDTO.setContents(publicationEntity.getContents());
        return publicationsDTO;
    }
}
