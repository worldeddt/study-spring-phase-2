package syncasync.webflux.services;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class WebFluxService {

    public Flux<String> helloWorld() {
        return Flux.just("Hello", "World");

    }
}
