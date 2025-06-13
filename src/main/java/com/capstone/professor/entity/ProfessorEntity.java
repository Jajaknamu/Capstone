package com.capstone.professor.entity;

import com.capstone.professor.dto.ProfessorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "professors_table")
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String professorName; //교수 이름, 필수

    @Column(nullable = false)
    private String professorEmail; //교수 이메일, 필수

    @Column
    private String major; //전공

    @Column
    private int likesCount; //교수 추천 수

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfessorCareersEntity> careers = new HashSet<>(); //학력

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfessorEducationEntity> educations = new HashSet<>(); //경력

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfessorPublicationsEntity> publications = new HashSet<>(); //논문 및 업적

    //연구실 모집글
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfessorLabEntity> labs = new HashSet<>();

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfessorRecommendationEntity> recommendations = new ArrayList<>();

    //dto -> entity 변환
    public static ProfessorEntity toProfessorEntity(ProfessorDTO professorDTO) {
        ProfessorEntity professorEntity = new ProfessorEntity();
        professorEntity.setId(professorDTO.getId());
        professorEntity.setProfessorName(professorDTO.getProfessorName());
        professorEntity.setProfessorEmail(professorDTO.getProfessorEmail());
        professorEntity.setMajor(professorDTO.getMajor());
        professorEntity.setLikesCount(professorDTO.getLikesCount());

        professorEntity.setCareers(new HashSet<>());
        professorEntity.setEducations(new HashSet<>());
        professorEntity.setPublications(new HashSet<>());
        professorEntity.setLabs(new HashSet<>());
        return professorEntity;
    }
}
