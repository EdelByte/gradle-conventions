import dev.edelbyte.gradle.*
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("java-core")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib")
    api("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$KOTLINX_COROUTINES_VERSION")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$KOTLINX_COROUTINES_VERSION")
}

kotlin {
    explicitApi()
    
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        progressiveMode = true
        jvmToolchain(17)
    }
}
