package prototype.aop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prototype.aop.services.ApiService;


@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/{id}")
    public void getter(@PathVariable int id) {

        log.info("getter id : {}", id);
    }

    @GetMapping("/ann/{id}")
    public void getterAnnotation(@PathVariable int id) {

        apiService.logService();
    }
}
