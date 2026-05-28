import io.edelbyte.gradle.SPRING_BOOT_VERSION
import org.gradle.kotlin.dsl.dependencies

description = "Convention Plugin for Java core configuration"

plugins {
    `java-library`
    id("io.freefair.lombok")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"

    // enables method parameter name introspection needed for Jackson
    options.compilerArgs.add("-parameters")
}

tasks.named<Test>("test") {
    useJUnitPlatform()

    maxParallelForks = Runtime.getRuntime().availableProcessors()
    systemProperty("file.encoding", "UTF-8")

    // As Mockito appends the bootstrap classpath, we turn of CDS to omit the warning
    jvmArgs("-Xshare:off")
}

// jars should be built reproducibly to improve docker layering
tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$SPRING_BOOT_VERSION"))
    implementation("org.apache.commons:commons-lang3")
    compileOnly("org.slf4j:slf4j-api")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}