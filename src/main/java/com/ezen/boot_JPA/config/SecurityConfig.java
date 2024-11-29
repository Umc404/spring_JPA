package com.ezen.boot_JPA.config;

import com.ezen.boot_JPA.security.CustomerUserService;
import com.ezen.boot_JPA.security.LoginFailureHandler;
import com.ezen.boot_JPA.security.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 주석에서 추가한 이유 : /error?continue 페이지 이동 방지

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();       // 기존에서 추가
        requestCache.setMatchingRequestParameterName(null);                         // 기존에서 추가

        return http
                .csrf(csrf -> csrf.disable())                                       // 기존에서 추가
                .requestCache(request -> request                                    // 기존에서 추가
                        .requestCache(requestCache))
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers("/js/**","/dist/**","/upload/**","/","/img/**",
                                        "/index","/user/join","/user/login","/board/list",
                                        "/comment/list/**").permitAll()
                                .requestMatchers("/user/list").hasAnyRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .usernameParameter("email")
                        .passwordParameter("pwd")
                        .loginPage("/user/login")
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                )
                .build();
    }

    @Bean
    UserDetailsService customerUserService() {
        return new CustomerUserService();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new LoginFailureHandler();
    }
}
