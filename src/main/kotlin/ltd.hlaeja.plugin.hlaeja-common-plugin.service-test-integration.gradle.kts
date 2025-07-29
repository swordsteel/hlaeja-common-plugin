plugins {
    id("idea")

    kotlin("jvm")
}

@Suppress("unused")
fun DependencyHandler.testIntegrationImplementation(
    dependencyNotation: Any,
): Dependency? = add("testIntegrationImplementation", dependencyNotation)

@Suppress("unused")
fun DependencyHandler.testIntegrationRuntimeOnly(
    dependencyNotation: Any,
): Dependency? = add("testIntegrationRuntimeOnly", dependencyNotation)

sourceSets.create("test-integration") {
    compileClasspath += sourceSets["main"].output
    runtimeClasspath += sourceSets["main"].output
    idea.module {
        testSources.from(sourceSets["test-integration"].kotlin.srcDirs, sourceSets["test-integration"].java.srcDirs)
        testResources.from(sourceSets["test-integration"].resources.srcDirs)
    }
    configurations.let {
        it["testIntegrationImplementation"].extendsFrom(configurations.implementation.get())
        it["testIntegrationRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())
    }
}

tasks {
    register<Test>("testIntegration") {
        description = "Runs test integration."
        group = "verification"
        testClassesDirs = sourceSets["test-integration"].output.classesDirs
        classpath = sourceSets["test-integration"].runtimeClasspath
    }
    check { dependsOn(getByName("testIntegration")) }
}
