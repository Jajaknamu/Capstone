/*

package com.capstone;

import com.capstone.member.entity.MemberEntity;
import com.capstone.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PasswordEncryptionRunner implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        //모든 회원 조회
        List<MemberEntity> members = memberRepository.findAll();

        for (MemberEntity member : members) {
            //이미 암호화된 비밀번호인지 확인
            if(!member.getMemberPassword().startsWith("$2a$") && !member.getMemberPassword().startsWith("$2b$")){
                //평문 비밀번호를 BCrypt방식으로 암호화
                String encode = passwordEncoder.encode(member.getMemberPassword());
                member.setMemberPassword(encode);

                //변경된 회원 정보 저장
                memberRepository.save(member);
            }
        }
        System.out.println("비밀번호 암호화 완료");

    }
}

*/
