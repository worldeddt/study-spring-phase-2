package springstudy.session.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request, Authentication authentication) {
        // 클라이언트의 IP 주소 가져오기
//        String clientIp = getClientIpAddress(request);

        log.debug("session id : {}", request.getSession().getId());

        String clientIp = request.getRemoteAddr();
        model.addAttribute("clientIp", clientIp);

        // 인증된 사용자의 아이디 가져오기
        String username = authentication.getName();
        model.addAttribute("username", username);

        return "home";
    }

    // 클라이언트의 IP 주소를 가져오는 메서드
    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 다중 프록시 환경일 경우 첫 번째 IP가 실제 클라이언트 IP
            return ip.split(",")[0];
        } else {
            return request.getRemoteAddr();
        }
    }
}
