plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "2.1.1"
}

group = "sh.edelbyte.gradle"
version = "0.1.0-SNAPSHOT"

gradlePlugin {
    website.set("https://github.com/EdelByte/gradle-conventions")
    vcsUrl.set("https://github.com/EdelByte/gradle-conventions.git")
}

afterEvaluate {
    gradlePlugin.plugins.getByName("sh.edelbyte.gradle.java-core").apply {
        displayName = "Java Core Convention"
        description = "Opinionated Java compiler settings, test frameworks, and reproducible builds."
        tags.set(listOf("java", "conventions", "lombok", "junit"))
    }
    
    gradlePlugin.plugins.getByName("sh.edelbyte.gradle.kotlin-core").apply {
        displayName = "Kotlin Core Convention"
        description = "Kotlin library conventions with coroutines, explicit API mode, and progressive mode."
        tags.set(listOf("kotlin", "conventions", "coroutines"))
    }
    
    gradlePlugin.plugins.getByName("sh.edelbyte.gradle.spring-boot-base").apply {
        displayName = "Spring Boot Base Convention"
        description = "Spring Boot application conventions with validation and configuration processor."
        tags.set(listOf("spring-boot", "conventions", "validation"))
    }
    
    gradlePlugin.plugins.getByName("sh.edelbyte.gradle.jacoco").apply {
        displayName = "JaCoCo Convention"
        description = "JaCoCo code coverage configuration with XML report generation."
        tags.set(listOf("jacoco", "coverage", "testing"))
    }
    
    gradlePlugin.plugins.getByName("sh.edelbyte.gradle.open-rewrite").apply {
        displayName = "OpenRewrite Convention"
        description = "OpenRewrite automated refactoring and migration setup."
        tags.set(listOf("openrewrite", "refactoring", "migration"))
    }
}

dependencies {
    implementation("io.freefair.gradle:lombok-plugin:9.5.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
    implementation("org.openrewrite:plugin:6.25.0")
}
