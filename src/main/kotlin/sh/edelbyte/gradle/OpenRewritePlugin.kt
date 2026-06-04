package sh.edelbyte.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.openrewrite.gradle.RewriteExtension

class OpenRewritePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("org.openrewrite.rewrite")

        project.extensions.configure<RewriteExtension> {
            setExportDatatables(true)
            activeStyle("org.openrewrite.java.GoogleJavaFormat")
        }

        project.dependencies {
            add("rewrite", "org.openrewrite.recipe:rewrite-spring:6.31.0")
            add("rewrite", "org.openrewrite.recipe:rewrite-migrate-java:8.83.1")
        }
    }
}
