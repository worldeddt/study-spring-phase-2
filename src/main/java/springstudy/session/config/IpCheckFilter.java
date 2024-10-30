package springstudy.session.config;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.util.*;

@Slf4j
public class IpCheckFilter implements Filter {

    // 허용된 IP 주소 목록을 InetAddress로 저장
    private Set<InetAddress> allowedInetAddresses;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 허용된 IP 주소 초기화
        try {
            allowedInetAddresses = new HashSet<>();
//            allowedInetAddresses.add(InetAddress.getByName("127.0.0.1"));    // IPv4 로컬호스트
//            allowedInetAddresses.add(InetAddress.getByName("0:0:0:0:0:0:0:1")); // IPv6 로컬호스트
//            allowedInetAddresses.add(InetAddress.getByName("::1"));           // IPv6 로컬호스트 축약형
            allowedInetAddresses.add(InetAddress.getByName("118.235.10.224")); // IPv4 로컬호스트
            // 추가 허용된 IP 주소를 여기서 추가
            // allowedInetAddresses.add(InetAddress.getByName("192.168.0.1"));
        } catch (UnknownHostException e) {
            throw new ServletException("허용된 IP 주소를 초기화하는 중 오류 발생", e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String path = httpRequest.getRequestURI();

        // 로그인 페이지와 정적 리소스에 대한 요청은 제외
        if (path.startsWith("/login") || path.startsWith("/resources/") ||
                path.startsWith("/h2-console/") || path.startsWith("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        // 클라이언트의 IP 주소 가져오기
        String ipAddress = getClientIpAddress(httpRequest);

        // IP 주소를 InetAddress로 변환
        InetAddress clientInetAddress = InetAddress.getByName(ipAddress);

        // 허용된 IP 주소인지 확인
        if (isAllowedIp(clientInetAddress)) {
            chain.doFilter(request, response);
        } else {
            log.warn("허용되지 않은 IP 주소로부터의 접근 시도: {}", ipAddress);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "허용되지 않은 IP 주소입니다.");
        }
    }

    @Override
    public void destroy() {
        // 정리 작업이 필요하다면 구현
    }

    // 허용된 IP 주소인지 확인하는 메서드
    private boolean isAllowedIp(InetAddress clientAddress) {
        for (InetAddress allowedAddress : allowedInetAddresses) {
            if (allowedAddress.equals(clientAddress)) {
                return true;
            }
        }
        return false;
    }

    // 클라이언트의 IP 주소를 가져오는 메서드 (프록시 환경 고려)
    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        log.info("client ip : {}", ip);

        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 다중 프록시 환경일 경우 첫 번째 IP가 실제 클라이언트 IP
            return ip.split(",")[0];
        } else {
            return request.getRemoteAddr();
        }
    }
}