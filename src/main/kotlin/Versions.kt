package io.edelbyte.gradle

/*
 Because pre-compiled convention plugin behave quite strange when it comes to managing versions,
 we cannot use any established ways like system properties or version catalogs (don't ask me why).
 So instead we just define the version as a constant in this Kotlin file.
 */

const val SPRING_BOOT_VERSION = "3.5.14"
const val DOCKER_JRE_BASE_IMAGE = "eclipse-temurin:17-jre-focal@sha256:602d6105a24722e3f43b288414d0f82c470369f9bd1cf80b43b29097633da42d"
