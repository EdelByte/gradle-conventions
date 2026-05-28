import org.gradle.kotlin.dsl.`java-library`

description = "Convention Plugin for collecting code coverage with JaCoCo"

plugins {
    `java-library`
    jacoco
}

jacoco {
    toolVersion = "0.8.14"
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
        html.required = false
    }
}

tasks.named<Test>("test") {
    finalizedBy(tasks.jacocoTestReport)
}