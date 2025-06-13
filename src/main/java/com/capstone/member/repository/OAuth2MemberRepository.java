package com.capstone.member.repository;

import com.capstone.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2MemberRepository extends JpaRepository<MemberEntity, Long> {

    MemberEntity findByMemberEmail(String memberEmail);
}
