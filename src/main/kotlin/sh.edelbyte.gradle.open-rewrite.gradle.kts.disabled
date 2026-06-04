
plugins {
    id("org.openrewrite.rewrite")
}

dependencies {
    rewrite("org.openrewrite.recipe:rewrite-spring:6.31.0")
    rewrite("org.openrewrite.recipe:rewrite-migrate-java:8.83.1")
}

rewrite {
    setExportDatatables(true)
    activeStyle("org.openrewrite.java.GoogleJavaFormat")
}

/*

To use OpenRewrite, apply this plugin in your build.gradle.kts file.
Then you can use the rewrite block to choose a recipe:

rewrite {
    activeRecipe("org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_3")
}

Then apply the recipe by running "gradle rewriteRun" or "gradle rewriteDryRun"

Recipe catalog: https://docs.openrewrite.org/recipes

When using this plugin you might need to include the memory for Gradle
For that set org.gradle.jvmargs=-Xmx6g in your gradle.properties

 */