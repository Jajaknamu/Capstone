package com.capstone.professor.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "professor_Education")
public class ProfessorEducationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String education; //학력

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professor; //교수님 정보
}
