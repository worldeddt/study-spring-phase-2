package syncasync.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import syncasync.webflux.services.WebFluxService;

@SpringBootTest
class WebfluxApplicationTests {

	private WebFluxService webFluxService = new WebFluxService();

	@Test
	void contextLoads() {
	}

	@Test
	void startFluxTest() {
		StepVerifier.create(webFluxService.startFlux())
				.expectNext(1,2,3,4,5,6,7,8,9,10)
				.verifyComplete();
	}

	@Test
	void fluxCountTest() {
		StepVerifier.create(webFluxService.fluxCount())
				.expectNext(10L)
				.verifyComplete();
	}



}
