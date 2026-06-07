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
        project.configurations.named(intTestSourceSet.runtimeOnlyConfigurationName).configure {
            extendsFrom(project.configurations.named("testRuntimeOnly").get())
        }
        project.configurations.named(intTestSourceSet.compileOnlyConfigurationName).configure {
            extendsFrom(project.configurations.named("testCompileOnly").get())
        }
        project.configurations.named(intTestSourceSet.annotationProcessorConfigurationName).configure {
            extendsFrom(project.configurations.named("testAnnotationProcessor").get())
        }

        project.dependencies {
            add(intTestSourceSet.implementationConfigurationName, "org.awaitility:awaitility")
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
