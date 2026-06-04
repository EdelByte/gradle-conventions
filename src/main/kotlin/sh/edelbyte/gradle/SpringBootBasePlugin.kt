package sh.edelbyte.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType

class SpringBootBasePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("sh.edelbyte.gradle.java-core")

        // enables method parameter name introspection needed for Jackson
        project.tasks.withType<JavaCompile>().configureEach {
            options.compilerArgs.add("-parameters")
        }

        // Mockito appends the bootstrap classpath, so we turn off CDS to omit the warning
        project.tasks.named<Test>("test") {
            jvmArgs("-Xshare:off")
        }

        project.dependencies {
            add("implementation", platform("org.springframework.boot:spring-boot-dependencies:$SPRING_BOOT_VERSION"))
            add("implementation", "org.springframework.boot:spring-boot-starter-validation")
            add("annotationProcessor", "org.springframework.boot:spring-boot-configuration-processor")
            add("testImplementation", "org.springframework.boot:spring-boot-starter-test")
        }
    }
}
