package prototype.springlogin.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String KAKAO_USER_URL = "https://kapi.kakao.com/v2/user/me";

    @PostMapping("/kakao")
    public ResponseEntity<String> getAccessToken(
            @RequestBody Map<String, String> requestData,
            HttpServletResponse httpResponse
    ) throws JsonProcessingException {
        log.info("code : {}", requestData.get("code"));

        String code = requestData.get("code");

        if (code == null) { return ResponseEntity.badRequest().body("Invalid Code");}

        String requestBody = "grant_type=authorization_code"
                + "&client_id="
                + "&redirect_uri=http://localhost:5173/auth/kakao/callback"
                + "&code=" + code;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(KAKAO_TOKEN_URL, entity, Map.class);

        if (response.getBody() != null) {
            String accessToken = (String) response.getBody().get("access_token");

            if (accessToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to get access token");
            }

            log.info("access token : {}", accessToken);
//            HttpHeaders headersUser = new HttpHeaders();
//            headersUser.setBearerAuth(accessToken);
//            HttpEntity<String> entityUser = new HttpEntity<>(headers);
//            ResponseEntity<Map> user = restTemplate.postForEntity(KAKAO_USER_URL, entityUser, Map.class);

//            log.info("user : {}", new ObjectMapper().writeValueAsString(user));

            Cookie cookie = new Cookie("access_token", accessToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // HTTPS 환경에서만 사용
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60); // 1시간 유지

            httpResponse.addCookie(cookie);

            return ResponseEntity.ok("로그인 성공");

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to get access token body");
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        // ✅ 쿠키를 빈 값으로 설정하고 Max-Age를 0으로 만들어 삭제
        Cookie cookie = new Cookie("access_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS 환경에서만 사용
        cookie.setPath("/"); // 전체 도메인에서 적용
        cookie.setMaxAge(0); // ✅ 즉시 만료
        response.addCookie(cookie);

        return "로그아웃 성공";
    }
}
