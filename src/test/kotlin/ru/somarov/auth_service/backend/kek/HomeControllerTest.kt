package ru.somarov.auth_service.backend.kek

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
@RunWith(SpringRunner::class)
@ActiveProfiles(profiles = ["test"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomeControllerTest {
    @Test
    fun `controller2 test`() {
        StepVerifier.create(Mono.just("Mono"))
                .expectSubscription()
                .expectNextCount(1)
                .expectComplete()
                .verify()
    }
}