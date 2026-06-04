package sh.edelbyte.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class KotlinCorePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("org.jetbrains.kotlin.jvm")
        project.pluginManager.apply("sh.edelbyte.gradle.java-core")

        project.extensions.configure<KotlinJvmProjectExtension> {
            explicitApi()
            jvmToolchain(17)
            compilerOptions {
                freeCompilerArgs.set(listOf("-Xjsr305=strict"))
                progressiveMode.set(true)
            }
        }

        project.dependencies {
            add("implementation", platform("org.jetbrains.kotlin:kotlin-bom"))
            add("api", "org.jetbrains.kotlin:kotlin-stdlib")
            add("api", "org.jetbrains.kotlin:kotlin-reflect")
            add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-core:$KOTLINX_COROUTINES_VERSION")
            add("testImplementation", "org.jetbrains.kotlin:kotlin-test-junit")
            add("testImplementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test:$KOTLINX_COROUTINES_VERSION")
        }
    }
}
