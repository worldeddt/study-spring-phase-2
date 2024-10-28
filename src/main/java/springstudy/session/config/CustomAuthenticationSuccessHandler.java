package springstudy.session.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 사용자 IP 주소 가져오기
        String ipAddress = request.getRemoteAddr();


        log.debug("ip address: {}", ipAddress);
        // 세션에 IP 주소 저장
        request.getSession().setAttribute("ipAddress", ipAddress);
        log.debug("session : {}/{}", request.getSession().getId(), request.getSession());

        // 기본 설정된 페이지로 리다이렉트
        response.sendRedirect("/home");
    }
}
