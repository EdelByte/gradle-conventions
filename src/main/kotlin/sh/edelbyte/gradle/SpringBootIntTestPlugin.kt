package sh.edelbyte.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

class SpringBootIntTestPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        project.pluginManager.apply("sh.edelbyte.gradle.spring-boot-base")

        val sourceSets = project.extensions.getByType<SourceSetContainer>()
        val mainSourceSet = sourceSets.named(SourceSet.MAIN_SOURCE_SET_NAME)
        val testSourceSet = sourceSets.named(SourceSet.TEST_SOURCE_SET_NAME)
        val intTestSourceSet = sourceSets.create("intTest") {
            java.srcDir("src/it/java")
            resources.srcDir("src/it/resources")

            compileClasspath += mainSourceSet.get().output + testSourceSet.get().output
            runtimeClasspath += mainSourceSet.get().output + testSourceSet.get().output
        }

        project.configurations.named(intTestSourceSet.implementationConfigurationName).configure {
            extendsFrom(project.configurations.named("testImplementation").get())
        }

        project.dependencies {
            add(intTestSourceSet.implementationConfigurationName, platform("org.springframework.cloud:spring-cloud-dependencies:2023.0.5"))
            add(intTestSourceSet.implementationConfigurationName, "org.springframework.cloud:spring-cloud-contract-wiremock")
        }

        val intTest = project.tasks.register<Test>("intTest") {
            description = "Runs integration tests"
            testClassesDirs = intTestSourceSet.output.classesDirs
            classpath = intTestSourceSet.runtimeClasspath
            useJUnitPlatform()


            jvmArgs(
                // allows ordered execution of test classes
                "-Djunit.jupiter.testclass.order.default=org.junit.jupiter.api.ClassOrderer\$OrderAnnotation",
                // As Mockito appends the bootstrap classpath, we turn off CDS to omit the warning
                "-Xshare:off",
            )
            testLogging {
                exceptionFormat = TestExceptionFormat.FULL
            }
            shouldRunAfter("test")
        }

        project.tasks.named("check") {
            dependsOn(intTest)
        }
    }
}
