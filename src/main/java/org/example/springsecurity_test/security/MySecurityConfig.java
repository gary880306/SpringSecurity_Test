package org.example.springsecurity_test.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // 設定 Session 的創建機制
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .csrf(csrf -> csrf.disable())

                .addFilterBefore(new MyFilter(), BasicAuthenticationFilter.class)

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())

                .authorizeHttpRequests(request -> request
                        // 註冊功能
                        .requestMatchers("/register").permitAll()

                        // 登入功能
                        .requestMatchers("/userLogin").authenticated()

                        .requestMatchers("/getMovies").hasAnyRole("NORMAL_MEMBER","MOVIE_MANAGER","ADMIN")
                        .requestMatchers("/watchFreeMovie").hasAnyRole("NORMAL_MEMBER","ADMIN")
                        .requestMatchers("/watchVipMovie").hasAnyRole("VIP_MEMBER","ADMIN")
                        .requestMatchers("/uploadMovie").hasAnyRole("MOVIE_MANAGER","ADMIN")
                        .requestMatchers("/deleteMovie").hasAnyRole("MOVIE_MANAGER","ADMIN")
                        .requestMatchers("/subscribe","/unsubscribe").hasAnyRole("NORMAL_MEMBER","ADMIN")

                        .anyRequest().denyAll()
                )

                .build();
    }
}