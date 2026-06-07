plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "2.1.1"
}

group = "sh.edelbyte.gradle"
version = "0.2.0"

gradlePlugin {
    website.set("https://github.com/EdelByte/gradle-conventions")
    vcsUrl.set("https://github.com/EdelByte/gradle-conventions.git")

    plugins {
        create("javaCore") {
            id = "sh.edelbyte.gradle.java-core"
            implementationClass = "sh.edelbyte.gradle.JavaCorePlugin"
            displayName = "Java Core Convention"
            description = "Opinionated Java compiler settings, test frameworks, and reproducible builds."
            tags.set(listOf("java", "conventions", "lombok", "junit"))
        }
        create("jacocoCoverage") {
            id = "sh.edelbyte.gradle.jacoco"
            implementationClass = "sh.edelbyte.gradle.JacocoCoveragePlugin"
            displayName = "JaCoCo Convention"
            description = "JaCoCo code coverage configuration with XML report generation."
            tags.set(listOf("jacoco", "coverage", "testing"))
        }
        create("kotlinCore") {
            id = "sh.edelbyte.gradle.kotlin-core"
            implementationClass = "sh.edelbyte.gradle.KotlinCorePlugin"
            displayName = "Kotlin Core Convention"
            description = "Kotlin library conventions with coroutines, explicit API mode, and progressive mode."
            tags.set(listOf("kotlin", "conventions", "coroutines"))
        }
        create("springBootBase") {
            id = "sh.edelbyte.gradle.spring-boot-base"
            implementationClass = "sh.edelbyte.gradle.SpringBootBasePlugin"
            displayName = "Spring Boot Base Convention"
            description = "Spring Boot application conventions with validation and configuration processor."
            tags.set(listOf("spring-boot", "conventions", "validation"))
        }
        create("springBootService") {
            id = "sh.edelbyte.gradle.spring-boot-service"
            implementationClass = "sh.edelbyte.gradle.SpringBootServicePlugin"
            displayName = "Spring Boot Service Convention"
            description = "Convention Plugin for Spring Boot micro services"
            tags.set(listOf("spring-boot", "conventions", "service"))
        }
        create("springBootIntTest") {
            id = "sh.edelbyte.gradle.spring-boot-int-test"
            implementationClass = "sh.edelbyte.gradle.SpringBootIntTestPlugin"
            displayName = "Spring Boot Integration Test Convention"
            description = "Convention Plugin for running integration tests on Spring Boot services"
            tags.set(listOf("spring-boot", "conventions", "integration-test"))
        }
        create("openRewrite") {
            id = "sh.edelbyte.gradle.open-rewrite"
            implementationClass = "sh.edelbyte.gradle.OpenRewritePlugin"
            displayName = "OpenRewrite Convention"
            description = "OpenRewrite automated refactoring and migration setup."
            tags.set(listOf("openrewrite", "refactoring", "migration"))
        }
    }
}

dependencies {
    implementation("io.freefair.gradle:lombok-plugin:9.5.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
    implementation("org.openrewrite:plugin:6.25.0")
}
