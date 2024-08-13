package com.example.blogdemo.config;

import com.example.blogdemo.Service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    private final UserDetailService userService;

    @Bean
    public WebSecurityCustomizer configure() { // 정적 리소스와 h2 console 하위 url에 대해서 security를 적용하지 않는다.
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/statc/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //http 요청에 대한 보안 설정
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/login", "/signup", "/user").permitAll() //"login", "/signup", "/user" url로 access하는 경우 인증/인가없이도 누구나 접근할 수 있도록 설정
                    .anyRequest().authenticated() //이외의 경우 인증이 필요
            )
            .formLogin(formLogin -> // 폼 로그인 시
                formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/articles", true)
            )
            .logout(logout ->
                logout
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
            )
            .csrf(AbstractHttpConfigurer::disable); //  .csrf(csrf -> csrf.disable()) 메서드 참조 연산자 이용해서 csrf 비활성화
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
        BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception { // 인증 관리자 설정
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//            new AuthenticationManagerBuilder(http.getSharedObject(AuthenticationConfiguration.class).getAuthenticationManagerFactory());
//
//        authenticationManagerBuilder
//                .userDetailsService(userService)
//                .passwordEncoder(bCryptPasswordEncoder);
//
//        return authenticationManagerBuilder.build();

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { // 패스워드 인코더
        return new BCryptPasswordEncoder();
    }
}
