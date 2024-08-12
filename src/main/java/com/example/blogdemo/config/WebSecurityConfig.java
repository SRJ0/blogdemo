package com.example.blogdemo.config;

import com.example.blogdemo.Service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
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
    public WebSecurityCustomizer configure() { // static 파일 및 h2 console에 대해서 security를 실행하지 않는다.
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/statc/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/login", "/signup", "/user").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/articles", true)
            )
            .logout(logout ->
                logout
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
            )
            .csrf(AbstractHttpConfigurer::disable); //  .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
