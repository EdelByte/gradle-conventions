package sh.edelbyte.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoCoveragePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("java-library")
        project.pluginManager.apply("jacoco")

        project.extensions.configure<JacocoPluginExtension> {
            toolVersion = "0.8.14"
        }

        project.tasks.named<JacocoReport>("jacocoTestReport") {
            reports {
                xml.required.set(true)
                html.required.set(false)
            }
        }

        project.tasks.named<Test>("test") {
            finalizedBy(project.tasks.named("jacocoTestReport"))
        }
    }
}
