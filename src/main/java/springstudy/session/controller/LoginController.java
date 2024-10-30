package springstudy.session.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import springstudy.session.entities.User;
import springstudy.session.repository.UserRepository;

import java.util.Collections;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if ("duplicate_ip".equals(error)) {
            model.addAttribute("error", "이미 다른 IP에서 로그인되어 있습니다.");
        } else if (error != null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        return "login";
    }

    // 로그인 처리
    @PostMapping("/perform_login")
    public String performLogin(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                // 사용자 없음
                return "redirect:/login?error=true";
            }
            log.info("===================== 유저 존재 =====================");

            // 비밀번호 비교
            if (!passwordEncoder.matches(password, user.getPassword())) {
                // 비밀번호 불일치
                return "redirect:/login?error=true";
            }

            log.info("===================== 비밀번호 일치 =====================");

            // 인증 성공 시 Authentication 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), Collections.emptyList());

            // SecurityContext에 인증 정보 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("context : {}", SecurityContextHolder.getContext());

            // 세션 생성 및 설정
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // 로그인 성공 후 홈 페이지로 리다이렉트
            return "redirect:/home";
        } catch (AuthenticationException e) {
            // 인증 실패 시 로그인 페이지로 리다이렉트 (에러 파라미터 포함)
            return "redirect:/login?error=true";
        }
    }
}
