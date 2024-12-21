package syncasync.webflux.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class WebFluxServiceTest {


    @Autowired
    WebFluxService webFluxService;

    @Test
    void fluxHelloworld() {
        StepVerifier.create(
                webFluxService.helloWorld()
        ).expectNext("Hello World")
                .verifyComplete();
    }

}