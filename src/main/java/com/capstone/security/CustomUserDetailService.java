package com.capstone.security;

import com.capstone.member.entity.MemberEntity;
import com.capstone.member.repository.MemberRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@lombok.extern.slf4j.Slf4j
@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        //로그 확인용
        log.info("로그인 시도: {}", memberEmail);

        Optional<MemberEntity> memberEntity = memberRepository.findByMemberEmail(memberEmail);

        //값이 있는 경우 - 저장된 아이디 o
        if(memberEntity.isPresent()) {
            System.out.println("DB에서 찾은 사용자: " + memberEntity.get().getMemberEmail()); // 로그 추가

            log.info("사용자 찾음: {}", memberEntity.get().getMemberEmail());
            return new CustomUserDetails(memberEntity.get());
        } else {
            log.error("사용자를 찾을 수 없습니다: {}", memberEmail);
            throw  new UsernameNotFoundException("사용자를 찾을 수 없음" + memberEmail);
        }
    }
}
