package com.capstone.member;

import com.capstone.member.dto.MemberDTO;
import com.capstone.member.entity.MemberEntity;
import com.capstone.member.repository.MemberRepository;
import com.capstone.member.service.MemberService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

@RunWith(SpringRunner.class)@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //given - 테스트 데이터 생성(회원정보 기입)
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test@email.com");
        memberDTO.setMemberPassword("test");
        memberDTO.setMemberName("테스트");

        //when: 서비스의 save 메서드 호출
        memberService.save(memberDTO);

        //then: MemberRepository의 save메서드가 올바르게 호출되었는지 검증
        Optional<MemberEntity> saveMember = memberRepository.findByMemberEmail("test@email.com");
        Assert.assertTrue(saveMember.isPresent());
        Assert.assertEquals("test@eamil.com", saveMember.get().getMemberEmail());
        Assert.assertEquals("test", saveMember.get().getMemberPassword());
        Assert.assertEquals("테스트", saveMember.get().getMemberName());
    }
}
