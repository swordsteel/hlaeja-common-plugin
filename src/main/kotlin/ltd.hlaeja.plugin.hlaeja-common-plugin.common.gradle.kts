plugins {
    id("ltd.hlaeja.plugin.hlaeja-common-plugin.common-detekt")
    id("ltd.hlaeja.plugin.hlaeja-common-plugin.common-ktlint")

    id("ltd.hlaeja.plugin.hlaeja-core-plugin")

    kotlin("jvm")
}

java.toolchain.languageVersion = JavaLanguageVersion.of(17)

kotlin.compilerOptions.freeCompilerArgs.addAll("-Xjsr305=strict")

tasks {
    named("build") {
        dependsOn("buildInfo")
    }

    register("buildInfo") {
        group = "hlaeja"
        description = "Prints the project name and version"

        doLast {
            println(info.nameVersion)
        }
    }

    register("projectInfo") {
        group = "hlaeja"
        description = "Prints project information"
        doLast {
            println()
            println("UTC Time: ${info.utcTimestamp}")
            println()
            println("Project group: ${project.group}")
            println("Project name: ${project.name}")
            println("Project version: ${project.version}")
            println("Project description: ${project.description ?: "N/A"}")
            println()
            println("Gradle version: ${GradleVersion.current().version}")
            println("Java JVM version: ${JavaVersion.current()}")
            println("Java toolchain version: ${java.toolchain.languageVersion.orNull ?: "N/A"}")
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

version = git.version()
