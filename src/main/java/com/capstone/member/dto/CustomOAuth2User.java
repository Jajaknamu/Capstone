package com.capstone.member.dto;

import com.capstone.member.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final OAuth2Response oAuth2Response;
    private Role role;

    public CustomOAuth2User(OAuth2Response oAuth2Response, Role role) {
        this.oAuth2Response = oAuth2Response;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return Role.USER.toString();
            }
        });
        return collection;
    }

    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    public String getUserName() {
        //이 값은 id로 씀
        return oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
    }
}
