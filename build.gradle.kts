import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN

plugins {
    alias(hlaeja.plugins.gradle.detekt)
    alias(hlaeja.plugins.kotlin.jvm)
    alias(hlaeja.plugins.core)

    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation(hlaeja.com.bmuschko.docker.gradle.plugin)
    implementation(hlaeja.io.gitlab.arturbosch.detekt.gradle.plugin)
    implementation(hlaeja.ltd.hlaeja.plugin.core)
    implementation(hlaeja.org.jetbrains.kotlin.gradle.plugin)
    implementation(hlaeja.org.jlleitschuh.ktlint.gradle.plugin)
    implementation(hlaeja.org.springframework.springboot.gradle.plugin)
}

description = "Hlæja Common Plugin"
group = "ltd.hlaeja.plugin"
version = git.version()

detekt {
    buildUponDefaultConfig = true
    basePath = projectDir.path
    source.from(DEFAULT_SRC_DIR_KOTLIN)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
    withSourcesJar()
}

kotlin.compilerOptions.freeCompilerArgs.addAll("-Xjsr305=strict")

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/swordsteel/${project.name}")
            name = "GitHubPackages"
            credentials {
                username = config.find("repository.user", "REPOSITORY_USER")
                password = config.find("repository.token", "REPOSITORY_TOKEN")
            }
        }
    }
    publications.create<MavenPublication>("mavenJava") { from(components["java"]) }
}

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
    withType<Detekt> {
        reports {
            html.required = false
            md.required = false
            sarif.required = true
            txt.required = false
            xml.required = false
        }
    }
    withType<Jar> {
        manifest.attributes.apply {
            put("Implementation-Title", project.name)
            put("Implementation-Version", project.version)
            put("Implementation-Vendor", info.vendorName)
            put("Built-By", System.getProperty("user.name"))
            put("Built-Git", "${git.currentBranch()} #${git.currentShortHash()}")
            put("Built-Gradle", project.gradle.gradleVersion)
            put("Built-JDK", System.getProperty("java.version"))
            put("Built-OS", "${System.getProperty("os.name")} v${System.getProperty("os.version")}")
            put("Built-Time", info.utcTimestamp)
        }
    }
}
