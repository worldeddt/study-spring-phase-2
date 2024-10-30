package springstudy.session.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class IpCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        log.debug("session : {}", session);

        if (session != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            String sessionIp = (String) session.getAttribute("ipAddress");
            String currentIp = request.getRemoteAddr();

            log.debug("sessionIp: {}", sessionIp);
            log.debug("currentIp: {}", currentIp);

            if (sessionIp != null && !sessionIp.equals(currentIp)) {
                // IP 주소가 다르면 세션 무효화 및 로그아웃 처리
                session.invalidate();
                SecurityContextHolder.clearContext();
                response.sendRedirect("/login?error=ip_mismatch");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
