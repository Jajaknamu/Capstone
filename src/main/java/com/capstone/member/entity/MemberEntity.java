package com.capstone.member.entity;

import com.capstone.board.entity.BoardEntity;
import com.capstone.member.dto.MemberDTO;
import com.capstone.professor.entity.ProfessorRecommendationEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter @Getter
@Table(name = "member_table")
public class MemberEntity {

    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true, nullable = false) // unique 제약조건 추가
    private String memberEmail;

    @Column(nullable = true) // OAuth2 회원은 비밀번호가 없을 수도 있음
    private String memberPassword;

    @Column
    private String memberName;

    //역할 추가
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; //기본값 USER로 설정

    //OAuth2 로그인 사용시 저장되는 정보

    @Column(nullable = true)// OAuth2 회원의 경우 provider 정보 저장
    private String provider;

    @Column(nullable = true)
    private String providerId;

    /*여기서부턴 회원 탈퇴 기능을 위한 추가 코드임*/
    //board 테이블과 참조
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardEntity> boardEntityList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfessorRecommendationEntity> professorRecommendationEntities;
    /*여기서까진 회원 탈퇴 기능을 위한 추가 코드임*/

    //dto -> entity 변환
    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }

}
