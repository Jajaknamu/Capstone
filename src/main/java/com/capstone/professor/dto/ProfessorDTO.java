package com.capstone.professor.dto;

import com.capstone.professor.entity.ProfessorEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@ToString
public class ProfessorDTO {

    private Long id;
    private String professorName;
    private String professorEmail;
    private String major;
    private int likesCount;
    private List<ProfessorCareersDTO> careers = new ArrayList<>(); //초기화 해줘야됨.
    private List<ProfessorEducationDTO> educations = new ArrayList<>();
    private List<ProfessorPublicationsDTO> publications = new ArrayList<>();

    //entity -> dto
    public static ProfessorDTO toProfessorDTO(ProfessorEntity professorEntity) {
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(professorEntity.getId());
        professorDTO.setProfessorName(professorEntity.getProfessorName());
        professorDTO.setProfessorEmail(professorEntity.getProfessorEmail());
        professorDTO.setMajor(professorEntity.getMajor());
        professorDTO.setLikesCount(professorEntity.getLikesCount());

        professorDTO.setCareers(professorEntity.getCareers().stream()
                .map(ProfessorCareersDTO::toProfessorCareersDTO)
                /*.map(career -> ProfessorCareerDTO.toProfessorCareerDTO(career)-> 자바8 부터 위에 코드 처럼 쓰면 됨.*/
                .collect(Collectors.toList()));

        professorDTO.setEducations(professorEntity.getEducations().stream()
                .map(ProfessorEducationDTO::toProfessorEducationDTO)
                .collect(Collectors.toList()));

        professorDTO.setPublications(professorEntity.getPublications().stream()
                .map(ProfessorPublicationsDTO::toProfessorPublicationsDTO)
                .collect(Collectors.toList()));
        return professorDTO;
    }
}
