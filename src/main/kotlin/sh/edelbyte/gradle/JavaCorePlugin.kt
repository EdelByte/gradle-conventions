package sh.edelbyte.gradle

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType

class JavaCorePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("java-library")
        project.pluginManager.apply("io.freefair.lombok")

        project.extensions.configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        project.tasks.withType<JavaCompile>().configureEach {
            options.encoding = "UTF-8"
            // enables method parameter name introspection needed for Jackson
            options.compilerArgs.add("-parameters")
        }

        project.tasks.named<Test>("test") {
            useJUnitPlatform()

            maxParallelForks = Runtime.getRuntime().availableProcessors()
            systemProperty("file.encoding", "UTF-8")

            // As Mockito appends the bootstrap classpath, we turn off CDS to omit the warning
            jvmArgs("-Xshare:off")
        }

        // jars should be built reproducibly to improve docker layering
        project.tasks.withType<AbstractArchiveTask>().configureEach {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
        }

        project.dependencies {
            add("implementation", "org.apache.commons:commons-lang3:$COMMONS_LANG3_VERSION")
            add("compileOnly", "org.slf4j:slf4j-api:$SLF4J_VERSION")

            add("testImplementation", platform("org.junit:junit-bom:$JUNIT_VERSION"))
            add("testImplementation", "org.junit.jupiter:junit-jupiter")
            add("testImplementation", "org.assertj:assertj-core:$ASSERTJ_VERSION")
        }
    }
}
