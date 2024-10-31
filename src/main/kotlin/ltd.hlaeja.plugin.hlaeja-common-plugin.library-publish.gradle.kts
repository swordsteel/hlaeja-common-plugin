import java.lang.System.getenv

plugins {
    `maven-publish`
}

publishing {
    repositories {

        fun retrieveConfiguration(
            property: String,
            environment: String,
        ): String? = project.findProperty(property)?.toString() ?: getenv(environment)

        maven {
            url = uri("https://maven.pkg.github.com/swordsteel/${project.name}")
            name = "GitHubPackages"
            credentials {
                username = retrieveConfiguration("repository.user", "REPOSITORY_USER")
                password = retrieveConfiguration("repository.token", "REPOSITORY_TOKEN")
            }
        }
    }
    publications.register("mavenJava", MavenPublication::class) { from(components["java"]) }
}
