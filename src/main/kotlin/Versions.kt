package sh.edelbyte.gradle

/*
 Because pre-compiled convention plugin behave quite strange when it comes to managing versions,
 we cannot use any established ways like system properties or version catalogs (don't ask me why).
 So instead we just define the version as a constant in this Kotlin file.
 */

const val SPRING_BOOT_VERSION = "3.5.14"
const val COMMONS_LANG3_VERSION = "3.17.0"
const val SLF4J_VERSION = "2.0.16"
const val JUNIT_VERSION = "5.12.2"
const val ASSERTJ_VERSION = "3.27.7"
const val KOTLINX_COROUTINES_VERSION = "1.10.1"
const val DOCKER_JRE_BASE_IMAGE = "eclipse-temurin:17-jre-focal@sha256:602d6105a24722e3f43b288414d0f82c470369f9bd1cf80b43b29097633da42d"
