package com.capstone.professor.dto;

import com.capstone.member.dto.MemberDTO;
import com.capstone.professor.entity.ProfessorLabEntity;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfessorLabDTO {

    private Long id;
    private String labIntro; // 연구실 소개
    private String labKeyword; // 연구실 키워드
    private String labSupport; // 지원방법
    private String labRequirements; // 지원자격
    private String labTreatment; //대우 조건
    private int labLikes; // 연구실 관심 수

    private MemberDTO memberDTO; // 멤버 정보 통째로 ㄱ
    private ProfessorDTO professorDTO; // 교수 정보 통째로 ㄱ

    //entity -> dto 변환
    public static ProfessorLabDTO toProfessorLabDTO(ProfessorLabEntity professorLabEntity) {
        ProfessorLabDTO professorLabDTO = new ProfessorLabDTO();
        professorLabDTO.setId(professorLabEntity.getId());
        professorLabDTO.setLabIntro(professorLabEntity.getLabIntro());
        professorLabDTO.setLabKeyword(professorLabEntity.getLabKeyword());
        professorLabDTO.setLabSupport(professorLabEntity.getLabSupport());
        professorLabDTO.setLabRequirements(professorLabEntity.getLabRequirements());
        professorLabDTO.setLabTreatment(professorLabEntity.getLabTreatment());
        professorLabDTO.setLabLikes(professorLabEntity.getLabLikes());

        //member
        professorLabDTO.setMemberDTO(MemberDTO.toMemberDTO(professorLabEntity.getMember()));

        //professor
        professorLabDTO.setProfessorDTO(ProfessorDTO.toProfessorDTO(professorLabEntity.getProfessor()));

        return professorLabDTO;
    }
}
