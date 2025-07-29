import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    id("ltd.hlaeja.plugin.hlaeja-core-plugin")
}

tasks {
    withType<ProcessResources> {
        val projectName = project.name
        val projectVersion = project.version
        filesMatching("**/application.yml") {
            filter {
                it.replace(
                    "%APP_NAME%",
                    projectName.split("-").joinToString(" ") { word -> word.uppercaseFirstChar() },
                )
            }
            filter { it.replace("%APP_VERSION%", projectVersion as String) }
            filter { it.replace("%APP_BUILD_TIME%", info.utcTimestamp) }
            filter { it.replace("%APP_BUILD_OS_NAME%", System.getProperty("os.name")) }
            filter { it.replace("%APP_BUILD_OS_VERSION%", System.getProperty("os.version")) }
            filter { it.replace("%APP_BUILD_GIT_COMMIT%", git.currentShortHash()) }
            filter { it.replace("%APP_BUILD_GIT_BRANCH%", git.currentBranch()) }
        }
        onlyIf { file("src/main/resources/application.yml").exists() }
    }
}
