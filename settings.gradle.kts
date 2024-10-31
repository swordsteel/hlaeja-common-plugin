import java.lang.System.getenv

fun getProperty(property: String): String = extra[property] as String

fun retrieveConfiguration(
    property: String,
    environment: String,
): String? = if (extra.has(property)) getProperty(property) else getenv(environment)

fun hlaejaRepository(repositoryHandler: RepositoryHandler) {
    repositoryHandler.maven {
        url = uri("https://maven.pkg.github.com/swordsteel/**")
        name = "GitHubPackages"
        credentials {
            username = retrieveConfiguration("repository.user", "REPOSITORY_USER")
            password = retrieveConfiguration("repository.token", "REPOSITORY_TOKEN")
        }
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenLocal()
        hlaejaRepository(this)
        gradlePluginPortal()
        mavenCentral()
    }
    versionCatalogs.create("hlaeja").from("ltd.hlaeja.catalog:hlaeja-version-catalog:${getProperty("catalog")}")
}

pluginManagement.repositories {
    mavenLocal()
    hlaejaRepository(this)
    gradlePluginPortal()
}

rootProject.name = "hlaeja-common-plugin"
