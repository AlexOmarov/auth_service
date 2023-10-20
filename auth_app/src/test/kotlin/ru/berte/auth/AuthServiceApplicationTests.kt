package ru.berte.auth

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.berte.auth.base.BaseIntegrationTest

@SpringBootTest
@ActiveProfiles(profiles = ["test"])
class AuthServiceApplicationTests : BaseIntegrationTest() {
    @Test
    fun contextLoads() {
    }


}
