package com.capstone.member.dto;

import com.capstone.member.entity.MemberEntity;
import com.capstone.member.entity.Role;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String provider;
    private Role role; //역할 추가
/*
    private List<LectureDTO> enrolledLectures; // 수강한 강의 목록
*/

    //entity -> dto 변환
    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setProvider(memberEntity.getProvider());
        memberDTO.setRole(memberEntity.getRole()); // 역할
        return memberDTO;
    }

}
