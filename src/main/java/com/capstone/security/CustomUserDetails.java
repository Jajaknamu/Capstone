package com.capstone.security;

import com.capstone.member.entity.MemberEntity;
import groovy.transform.Sealed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter @Sealed
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails, OAuth2User {

    public CustomUserDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
        this.attributes = null;//OAuth2 로그인 아님 -> null 처리
    }

    private MemberEntity memberEntity;
    private Map<String, Object> attributes; // OAuth2 로그인 사용자 정보

    //사용자의 계정이 만료됐는지 -> 이거 사용하고 싶으면 테이블의 옵션 값 넣고 가져오면 됨
    @Override
    public boolean isAccountNonExpired() {
        return true; // 강제로 만료가 되지않았다는 걸로 설정
    }

    //사용자의 계정이 잠겼는지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //지금 사용가능 한지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // OAuth2User 인터페이스 메서드 추가
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //사용자의 특정한 권한을 리턴해주는 메서드 -> role 값을 리턴해주는 거임
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                // Role enum의 name() 메서드를 사용하여 문자열로 변환,name() 메서드를 호출하여 해당 enum 상수의 이름을 String으로 반환
                return memberEntity.getRole().name();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return memberEntity.getMemberPassword();
    }

    @Override
    public String getUsername() {
        return memberEntity.getMemberName();
    }

    @Override
    public String getName() {
        return "";
    }
}
