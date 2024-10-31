import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("ltd.hlaeja.plugin.hlaeja-common-plugin.common")
    id("ltd.hlaeja.plugin.hlaeja-common-plugin.library-manifest")
    id("org.springframework.boot")
}

java {
    withSourcesJar()
}

tasks {
    named<Jar>("jar") {
        archiveClassifier.set("")
    }
    withType<BootJar> {
        enabled = false
    }
}
