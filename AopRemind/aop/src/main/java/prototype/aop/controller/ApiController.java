package prototype.aop.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping("/api")
@RestController
public class ApiController {




    @GetMapping("/{id}")
    public void getter(@PathVariable int id) {

        log.info("getter id : {}", id);
    }
}
