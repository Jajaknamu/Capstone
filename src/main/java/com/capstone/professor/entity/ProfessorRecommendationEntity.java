package com.capstone.professor.entity;

import com.capstone.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "professor_recommendation")
public class ProfessorRecommendationEntity { //교수님 추천수 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // professor 삭제 시 recommendation 자동 삭제
    private ProfessorEntity professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    // 생성자 추가
    /*public ProfessorRecommendationEntity(ProfessorEntity professor, MemberEntity member) {
        this.professor = professor;
        this.member = member;
    }*/
}

