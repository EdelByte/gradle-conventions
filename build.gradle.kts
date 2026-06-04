plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "2.1.1"
}

group = "sh.edelbyte.gradle"
version = "0.1.0"

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
    }
}

dependencies {
    implementation("io.freefair.gradle:lombok-plugin:9.5.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
    implementation("org.openrewrite:plugin:6.25.0")
}
