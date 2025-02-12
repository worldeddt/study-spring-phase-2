package prototype.aop.services;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiService {

    @JsonIgnore
    public void logService() {
        log.info("log service");
    }
}
