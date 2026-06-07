package sh.edelbyte.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class SpringBootServicePlugin : Plugin<Project> {
    override fun apply(project: Project) {

        project.pluginManager.apply("sh.edelbyte.gradle.spring-boot-base")
        /*  NOTE: 
            The Spring Boot Plugin is not applied here by design,
            the service is meant to be built as a docker container via Jib
        */

        project.dependencies {
            add("implementation", "org.springframework.boot:spring-boot-starter-actuator")
            add("implementation", "org.springframework.boot:spring-boot-starter-aop")
            add("implementation", "org.springframework.boot:spring-boot-starter-web")
            add("implementation", "org.springframework.retry:spring-retry")
        }
    }
}
