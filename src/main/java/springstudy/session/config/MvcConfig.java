package springstudy.session.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 뷰 컨트롤러 설정
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 로그인 페이지 매핑
        registry.addViewController("/login").setViewName("login");
        // 홈 페이지 매핑
        registry.addViewController("/home").setViewName("home");
    }
}
