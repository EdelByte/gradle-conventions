# Gradle Convention Plugins

Opinionated Gradle convention plugins for consistent, low-maintenance JVM builds.

Stop copy-pasting `build.gradle.kts` boilerplate across modules. Apply one plugin ID, get a standardized build.

## The Problem

Multi-module Gradle projects accumulate duplicated build configuration — compiler settings, test frameworks, dependency management, publishing config — scattered across dozens of `build.gradle.kts` files. Changes require touching every module. Consistency is a myth.

## The Solution

Convention plugins encode your build standards once. Every module that applies a plugin inherits the same compiler flags, test setup, dependency constraints, and publishing configuration — with zero copy-paste.


## Available Plugins

| Plugin ID                     | Purpose                                                                                           |
|-------------------------------|---------------------------------------------------------------------------------------------------|
| `java-core`                   | Java library conventions — compiler settings, test frameworks, reproducible builds                |
| `spring-boot-base`            | Spring Boot Java application conventions — extends `java-core`, adds Spring Boot BOM              |
| `kotlin-core`                 | Kotlin library conventions — extends `java-core`, Kotlin compiler settings, stdlib                |
| `jacoco`                      | JaCoCo code coverage configuration                                                                |
| `open-rewrite`                | OpenRewrite automated refactoring setup                                                           |
