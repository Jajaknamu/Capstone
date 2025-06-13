package com.capstone.config;

import com.capstone.security.CustomOAuth2UserService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration // 스프링이 관리하는 설정 클래스 -> 시큐리티 설정해줄거임
@EnableWebSecurity //스프링 시큐리티가 관리한다는 뜻
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    //비밀번호 암호호를 위한 메서드
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //특정한 경로를 거부,허용 해주는 메서드
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/member/save", "/member/login","/member/email-check",
                                "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                );
        //csrf는 항상 켜두어야함. 공부적 허용으로 꺼둠
        http
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/member/save", "/member/email-check","/member/login")
                );
        //커스텀 로그인 설정
        http
                .formLogin((auth) -> auth
                        // 사용자 정의 로그인 페이지 설정
                        .loginPage("/")
                        // 로그인 처리 URL 설정
                        .loginProcessingUrl("/member/login")
                        // 로그인 성공 후 이동할 경로
                        .defaultSuccessUrl("/home",true)
                        // 로그인 실패 시 이동할 경로
                        .failureUrl("/member/login?error=true")
                        // 로그인 폼의 파라미터 이름을 memberEmail로 지정
                        .usernameParameter("memberEmail")
                        // 비밀번호 파라미터 이름을 memberPassword로 지정
                        .passwordParameter("memberPassword")
                        // 로그인 페이지는 모든 사용자에게 허용
                        .permitAll()

                );
        http
                .oauth2Login((auth) -> auth
                        .loginPage("/") //OAtuth2 로그인 페이지를 기존 로그인 폼과 동일하게 설정
                        .defaultSuccessUrl("/home",true) //OAuth2 로그인 성공 후 이동할 경로
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) //Bean으로 주입된 `CustomOAuth2UserService` 사용
                        )
                );
        //세션 고정 보호
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()
                );
        return http.build();
    }

    //정적 파일들은 필터처리에서 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

}
