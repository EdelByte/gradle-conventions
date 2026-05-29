import sh.edelbyte.gradle.SPRING_BOOT_VERSION
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("sh.edelbyte.gradle.java-core")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$SPRING_BOOT_VERSION"))
    implementation("org.springframework.boot:spring-boot-starter-validation")
    
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
