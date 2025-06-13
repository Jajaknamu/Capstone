package com.capstone.professor.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Professor_publication")
public class ProfessorPublicationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String title; //논문 및 업적 제목

    @Column(length = 1000)
     private String contents; // 논문 및 업적 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professor; //교수님 정보
}
