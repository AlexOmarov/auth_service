package ru.somarov.auth_service

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.logging.Level
import java.util.logging.LogManager

@SpringBootTest
@ActiveProfiles(profiles = ["test"])
class AuthServiceApplicationTests {
    @Test
    fun contextLoads() {
    }


}
