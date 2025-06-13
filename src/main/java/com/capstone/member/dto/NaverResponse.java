package com.capstone.member.dto;

import java.util.Map;

//네이버에서 정보를 받는 dto
public class NaverResponse implements OAuth2Response{
    //네이버 로그인 API에사 받은 사용자 정보를 저장하는 변수
    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
