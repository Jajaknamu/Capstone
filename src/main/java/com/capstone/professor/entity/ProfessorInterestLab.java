package com.capstone.professor.entity;

import com.capstone.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
//이렇게 하면 DB 레벨에서 같은 연구실 + 같은 회원의 중복 추천을 막을 수 있고 폼 로그인/소셜 로그인 관계없이 안전하게 중복 insert 방지 가능
@Table(name = "professor_interestlab", uniqueConstraints = {@UniqueConstraint(columnNames = {"professorlab_id", "member_id"})})
public class ProfessorInterestLab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professorlab_id")
    private ProfessorLabEntity professorLab;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;
}
