plugins {
    id("ltd.hlaeja.plugin.hlaeja-core-plugin")
    `maven-publish`
}

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
    publications.register("mavenJava", MavenPublication::class) { from(components["java"]) }
}
