package ru.berte.auth.arch

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.TestInstance
import org.springframework.transaction.annotation.Transactional

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AnalyzeClasses(packages = ["ru.berte.auth"])
class ArchUnitTests {
    @ArchTest
    fun `there are no transactional annotation`(importedClasses: JavaClasses) {
        noClasses().should().beAnnotatedWith(Transactional::class.java).check(importedClasses)
    }
}
