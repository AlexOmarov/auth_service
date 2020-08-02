import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
}

group = "ru.somarov"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8
extra["micrometer.version"] = "1.4.2"

val developmentOnly: Configuration by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.springframework.boot.experimental:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-rsocket")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")


    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("ch.qos.logback:logback-classic")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.micrometer:micrometer-core")


    implementation("io.opentracing.contrib:opentracing-spring-jaeger-cloud-starter:3.1.2")

    implementation("org.flywaydb:flyway-core:6.3.3")

    implementation("net.logstash.logback:logstash-logback-encoder:6.4")
    implementation("io.github.microutils:kotlin-logging:1.8.3")



    // do deps independently. in such way we can easily connect every opentracing implementation.
    // the only thing to do is to configure the tracer bean
/*    implementation("io.opentracing.contrib:opentracing-spring-cloud-starter:0.5.6")
    implementation("io.jaegertracing:jaeger-client:1.3.1")*/


    //implementation("org.springframework.security:spring-security-messaging")
    //implementation("org.springframework.security:spring-security-rsocket")
    //implementation("org.springframework.session:spring-session-core")
    //implementation("org.springframework.session:spring-session-data-redis")
    //implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")



    runtimeOnly("io.r2dbc:r2dbc-postgresql")
    runtimeOnly("io.r2dbc:r2dbc-h2")
    runtimeOnly("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.testcontainers:testcontainers:1.13.0")
    testImplementation("org.testcontainers:junit-jupiter:1.13.0")
    testImplementation("org.testcontainers:postgresql:1.13.0")

    //testImplementation("org.flywaydb.flyway-test-extensions:flyway-spring-test:6.3.3")

    testImplementation("org.springframework.boot.experimental:spring-boot-test-autoconfigure-r2dbc")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot.experimental:spring-boot-bom-r2dbc:0.1.0.M3")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

gradle.buildFinished {
    val srcDir = File("$buildDir/libs/${rootProject.name}-${version}.jar")
    if (srcDir.exists()) {
        println("Extracting deps")
        delete("build/deps")
        mkdir("build/deps")
        org.gradle.kotlin.dsl.support.unzipTo(file("build/deps"), srcDir)
    } else {
        println("No jar found, please build the project first")
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
