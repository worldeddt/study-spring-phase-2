package springstudy.graphqlExample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/*").permitAll()
                )
                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/home")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                // 세션 관리 설정
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false) // 중복 로그인 허용 (커스텀 로직으로 제어)

                )
                // CSRF 설정 (H2 콘솔 사용 시 비활성화)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                // H2 콘솔 사용을 위한 프레임 옵션 설정
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                );

        return http.build();
    }
}
