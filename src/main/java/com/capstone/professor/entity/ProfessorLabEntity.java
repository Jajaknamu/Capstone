package com.capstone.professor.entity;

import com.capstone.member.entity.MemberEntity;
import com.capstone.professor.dto.ProfessorLabDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "professor_lab")
public class ProfessorLabEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String labIntro; // 연구실 소개
    @Column
    private String labKeyword; // 연구분야 키워드
    @Column(length = 500)
    private String labSupport; // 연구실 지원방법
    @Column(length = 500)
    private String labRequirements; //자격 조건
    @Column(length = 500)
    private String labTreatment; // 대우 조건

    //연구실 관심 수
    @Column
    private int labLikes = 0; // 기본값 설정 해줘야 db에 null이 안들어감

    //professor 테이블과 참조
    //professor:lab 1:n -> 교수 1명 당 여러개의 연구실 작성 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private ProfessorEntity professor;

    //나중에 연구실 관심추가 기능 추가할때 사용할거
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    //dto -> entity 변환
    public static ProfessorLabEntity toProfessorLabEntity(ProfessorLabDTO professorLabDTO) {
        ProfessorLabEntity professorLabEntity = new ProfessorLabEntity();
        professorLabEntity.setLabIntro(professorLabDTO.getLabIntro());
        professorLabEntity.setLabKeyword(professorLabDTO.getLabKeyword());
        professorLabEntity.setLabSupport(professorLabDTO.getLabSupport());
        professorLabEntity.setLabRequirements(professorLabDTO.getLabRequirements());
        professorLabEntity.setLabTreatment(professorLabDTO.getLabTreatment());
        professorLabEntity.setLabLikes(professorLabDTO.getLabLikes());

        //member
        professorLabEntity.setMember(MemberEntity.toMemberEntity(professorLabDTO.getMemberDTO()));

        //professor 연관관계 매핑
        professorLabEntity.setProfessor(ProfessorEntity.toProfessorEntity(professorLabDTO.getProfessorDTO()));
        return professorLabEntity;
    }
}


