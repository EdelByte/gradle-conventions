# Gradle Convention Plugins

Opinionated Gradle convention plugins for consistent, low-maintenance JVM builds.

Stop copy-pasting `build.gradle.kts` boilerplate across modules. Apply one plugin ID, get a standardized build.

## The Problem

Multi-module Gradle projects accumulate duplicated build configuration — compiler settings, test frameworks, dependency management, publishing config — scattered across dozens of `build.gradle.kts` files. Changes require touching every module. Consistency is a myth.

## The Solution

Convention plugins encode your build standards once. Every module that applies a plugin inherits the same compiler flags, test setup, dependency constraints, and publishing configuration — with zero copy-paste.
This repo contains a set of __opinionated__ convention plugins to help set sensible defaults for projects.


## Demo Repository

To see these convention plugins in action, including how they are applied in real module `build.gradle.kts` files, see the demo repository: [Demo project](https://github.com/<your-org>/<your-demo-repo>).

## Available Plugins

| Plugin ID                     | Purpose                                                                                           |
|-------------------------------|---------------------------------------------------------------------------------------------------|
| `java-core`                   | Java library conventions — compiler settings, test frameworks, reproducible builds                |
| `spring-boot-base`            | Spring Boot Java application conventions — extends `java-core`, adds Spring Boot BOM              |
| `spring-boot-service`         | Spring Boot service conventions — extends `spring-boot-base`, adds actuator, web, AOP, retry      |
| `spring-boot-int-test`        | Spring Boot integration-test conventions — adds `intTest` source set/task and test wiring         |
| `kotlin-core`                 | Kotlin library conventions — extends `java-core`, Kotlin compiler settings, stdlib                |
| `jacoco`                      | JaCoCo code coverage configuration                                                                |
| `open-rewrite`                | OpenRewrite automated refactoring setup                                                           |

## Background

These conventions were built as the result of migrating a ~1M LOC project with 25 microservices and 35 libraries from Maven to Gradle, and they reflect the defaults and patterns that proved most effective during that migration.

