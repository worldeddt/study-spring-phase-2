package syncasync.webflux.services;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebFluxService {

    public Flux<String> helloWorld() {
        return Flux.just("Hello", "World");
    }

    public Flux<Integer> startFlux() {
        return Flux.range(1, 10).log();
    }

    public Mono<Long> fluxCount() {
        return Flux.range(1, 10).log()
                .count()
                .log();
    }
}
