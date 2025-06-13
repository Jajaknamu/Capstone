package com.capstone.security;

import com.capstone.member.dto.GoogleResponse;
import com.capstone.member.dto.NaverResponse;
import com.capstone.member.dto.OAuth2Response;
import com.capstone.member.entity.MemberEntity;
import com.capstone.member.entity.Role;
import com.capstone.member.repository.OAuth2MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuth2MemberRepository oAuth2MemberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //부모 클래스의 loadUser() 호출하여 OAuth2User 객체 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User = " + oAuth2User.getAttributes()); // 네이버, 구글 정보 로그출력

        //OAuth2 제공자(Google, Naver 등) 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        // 네이버 OAuth2 처리
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        // 구글 OAuth2 처리
        else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {
            return null; // 지원하지 않는 OAuth2 Provider
        }

        //전달받은 정보가 db에 존재하는지 확인
        MemberEntity existData = oAuth2MemberRepository.findByMemberEmail(oAuth2Response.getEmail());

        //db에 저장된 회원x -> 처음 로그인 하는 경우
        if (existData == null) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setMemberEmail(oAuth2Response.getEmail());
            memberEntity.setMemberPassword(null); // 소셜 로그인은 비밀번호 없음
            memberEntity.setMemberName(oAuth2Response.getName());
            memberEntity.setRole(Role.USER); // 기본 권한 설정
            memberEntity.setProvider(oAuth2Response.getProvider());
            memberEntity.setProviderId(oAuth2Response.getProviderId());

            oAuth2MemberRepository.save(memberEntity);
        }
        //이미 존재하는 경우 -> 처음 로그인x(변경된 것이 있으면 업데이트 하는 방식)
        else {
            existData.setMemberEmail(oAuth2Response.getEmail());
            oAuth2MemberRepository.save(existData);
        }
        // CustomUserDetails를 OAuth2User로 반환 (OAuth2UserService의 반환 타입과 일치)
        return new CustomUserDetails(existData, oAuth2User.getAttributes());
    }
}
