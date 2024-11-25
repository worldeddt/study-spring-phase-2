package springstudy.session.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import springstudy.session.components.SessionInformationWithIp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {

    @Autowired
    private SessionRegistry sessionRegistry;

    // 사용자별 세션과 IP 주소를 저장하는 맵
    private Map<String, SessionInformationWithIp> userSessionMap = new ConcurrentHashMap<>();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws java.io.IOException {
        String username = authentication.getName();
        String ipAddress = getClientIpAddress(request);

        // 기존 세션 정보 확인
        SessionInformationWithIp existingSessionInfo = userSessionMap.get(username);

        if (existingSessionInfo != null) {
            String existingIp = existingSessionInfo.getIpAddress();
            if (!existingIp.equals(ipAddress)) {
                // IP가 다르면 로그인 거부 (인증 실패로 처리)
                // SecurityContext에서 인증 정보 제거
                SecurityContextHolder.clearContext();

                // 세션 무효화
                request.getSession().invalidate();

                // 로그인 페이지로 리다이렉트하며 에러 메시지 전달
                response.sendRedirect("/login?error=duplicate_ip");
                return;
            }
        }

        // 현재 세션과 IP 정보 저장
        HttpSession session = request.getSession();
        log.debug("current session : {}", session.getId());
        System.out.println("current session : "+session.getId());
        sessionRegistry.registerNewSession(session.getId(), authentication);

        SessionInformation sessionInfo = sessionRegistry.getSessionInformation(session.getId());
        SessionInformationWithIp sessionInfoWithIp = new SessionInformationWithIp(sessionInfo, ipAddress);

        userSessionMap.put(username, sessionInfoWithIp);

        // 로그인 성공 후 홈 페이지로 리다이렉트

        response.sendRedirect("/home");
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

    public void removeSession(HttpSession session) {
        // 세션에 해당하는 사용자 찾기
        String username = null;
        for (Map.Entry<String, SessionInformationWithIp> entry : userSessionMap.entrySet()) {
            if (entry.getValue().getSessionInformation().getSessionId().equals(session.getId())) {
                username = entry.getKey();
                break;
            }
        }
        if (username != null) {
            userSessionMap.remove(username);
        }
    }
}
