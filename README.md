# Gradle Convention Plugins
This repo contains a collection of Gradle convention plugins, which provide some sensible default settings for building Java/Kotlin/Spring microservices.

### Convention Plugins
*Note: The order in which plugins are applied is important. Make sure to apply the `core` plugins first.*

| Plugin                   | Purpose                                                                          |
|--------------------------|----------------------------------------------------------------------------------|
| Java Spring Core         | Contains the basic setup for Java projects inlc. JUnit & Spring Boot 3           |
| Jacoco                   | Contains the configuration for JaCoCo                                            |
| Open Rewrite             | Contains the basic setup to run OpenRewrite migrations                           |
| Spring Service           | Contains the basic setup for Spring Boot services                                |
