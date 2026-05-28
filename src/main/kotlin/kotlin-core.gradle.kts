import org.gradle.kotlin.dsl.dependencies

plugins {
    id("java-spring-boot-core")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib")
    api("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmToolchain(17)
    }
}
