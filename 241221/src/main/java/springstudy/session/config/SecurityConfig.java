//package springstudy.session.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.session.HttpSessionEventPublisher;
//import springstudy.session.services.CustomUserDetailsService;
//
//@Configuration
//public class SecurityConfig {
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    private SessionRegistry sessionRegistry;
//
//    @Autowired
//    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
//
//    // PasswordEncoder 설정
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // AuthenticationProvider 설정
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(customUserDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    // SecurityFilterChain 설정
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // 권한 설정
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/login", "/h2-console/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                // 로그인 설정
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/perform_login")
//                        .defaultSuccessUrl("/home")
//                        .successHandler(customAuthenticationSuccessHandler) // 커스텀 AuthenticationSuccessHandler 등록
//                        .failureUrl("/login?error=true")
//                        .permitAll()
//                )
//                // 로그아웃 설정
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .invalidateHttpSession(true)
//                        .clearAuthentication(true)
//                        .permitAll()
//                )
//                // 세션 관리 설정
//                .sessionManagement(session -> session
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(false) // 중복 로그인 허용 (커스텀 로직으로 제어)
//                        .sessionRegistry(sessionRegistry)
//                )
//                // CSRF 설정 (H2 콘솔 사용 시 비활성화)
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
//                // H2 콘솔 사용을 위한 프레임 옵션 설정
//                .headers(headers -> headers
//                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
//                );
//
//        return http.build();
//    }
//
//    // HttpSessionEventPublisher 등록 (중복 로그인 제어를 위해 필요)
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//}
