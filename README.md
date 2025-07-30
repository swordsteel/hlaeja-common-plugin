# Hlæja Common Plugin

Plugins for the libraries, lofty and grand, Plugins for the services, steadfast as stone, Plugins for the common, bound to their fate, Using the one plugin to rule them all, and in automation bind them, In the realm of code, where the builds take form.

<!-- TOC -->
* [Hlæja Common Plugin](#hlæja-common-plugin)
  * [Plugins](#plugins)
    * [Common](#common)
      * [Plugin Common](#plugin-common)
        * [Gradle Tasks](#gradle-tasks)
      * [Plugin Common Detekt](#plugin-common-detekt)
      * [Plugin Common Ktlint](#plugin-common-ktlint)
    * [Library](#library)
      * [Plugin Library](#plugin-library)
      * [Plugin Library Manifest](#plugin-library-manifest)
      * [Plugin Library publish](#plugin-library-publish)
    * [Service](#service)
      * [Plugin Service](#plugin-service)
      * [Plugin Service Container](#plugin-service-container)
        * [Configuration](#configuration)
        * [Gradle Tasks](#gradle-tasks-1)
      * [Plugin Service Test Integration](#plugin-service-test-integration)
      * [Plugin Service Process Resource](#plugin-service-process-resource)
      * [Plugin Certificate](#plugin-certificate)
    * [UI Service](#ui-service)
      * [Plugin UI Service](#plugin-ui-service)
      * [Plugin UI Service Thymeleaf Minify](#plugin-ui-service-thymeleaf-minify)
  * [Releasing Plugin](#releasing-plugin)
  * [Publishing plugin](#publishing-plugin)
    * [Publish plugin locally](#publish-plugin-locally)
    * [Publish plugin to repository](#publish-plugin-to-repository)
    * [Global gradle properties](#global-gradle-properties)
<!-- TOC -->

## Plugins

### Common

#### Plugin Common

id `ltd.hlaeja.plugin.hlaeja-common-plugin.common`

Set core Java and Kotlin settings and overweight project version with git version.

##### Gradle Tasks

* `buildInfo` display name and version, add to `build` task.
* `projectInfo` display project, Gradle, and Java information

#### Plugin Common Detekt

id `ltd.hlaeja.plugin.hlaeja-common-plugin.common-detekt`

Detect is a code smell analysis for your Kotlin projects.

#### Plugin Common Ktlint

id `ltd.hlaeja.plugin.hlaeja-common-plugin.common-ktlint`

Ktlint enforces consistent code style and formatting across Kotlin codebases.

### Library

#### Plugin Library

id `ltd.hlaeja.plugin.hlaeja-common-plugin.library`

Default setting and tasks for libraries.

#### Plugin Library Manifest

id `ltd.hlaeja.plugin.hlaeja-common-plugin.library-manifest`

Extend manifest in library jar file.

#### Plugin Library publish

id `ltd.hlaeja.plugin.hlaeja-common-plugin.library-publish`

Configuration for publishing project artifacts to a remote Maven repository.

### Service

#### Plugin Service

id `ltd.hlaeja.plugin.hlaeja-common-plugin.service`

Default setting and tasks for services.

#### Plugin Service Container

id `ltd.hlaeja.plugin.hlaeja-common-plugin.service-container`

Configuration for running project in docker locally during development.

##### Configuration

* properties `container.network`, environment `CONTAINER_NETWORK`, or default `develop`
* properties `container.port.expose`, environment `CONTAINER_PORT_EXPOSE`, or default `8080`
* properties `container.port.host`, environment `CONTAINER_PORT_HOST`, or default `8080`
* properties `container.profiles`, environment `CONTAINER_PROFILES`, or default `docker`
* properties `docker.port.expose`, environment `DOCKER_PORT_EXPOSE`, or default `8080`

container and docker ports can be a single port (e.g., 8080) or multiple ports separated by commas (e.g., 8080,8443)

##### Gradle Tasks

* `containerCreate` create docker container with network and spring boot profile.
* `containerStart` starts docker container.
* `containerStop` stops docker container.
* `containerNetworkCheck` check if network exist.
* `containerNetworkCreate` creates network.
* `containerNetworkRemove` removes network.

#### Plugin Service Test Integration

id `ltd.hlaeja.plugin.hlaeja-common-plugin.service-test-integration`

Adding task `testIntegration` to run integration test, add to `verification` group and add to task `check`.

Adding intellij support `src/test-integration/java`, `src/test-integration/kotlin`, and `src/test-integration/resources` as test module in intellij.

Adding dependencies support `testIntegrationImplementation()`, and `testIntegrationRuntimeOnly()` as part of Gradle.

#### Plugin Service Process Resource

id `ltd.hlaeja.plugin.hlaeja-common-plugin.service-process-resource`

Assign values to `application.yml` from Gradle.

#### Plugin Certificate

id `ltd.hlaeja.plugin.hlaeja-common-plugin.cetificat`

Copy certificate to build resources by placing one or more of `private_key.pem`, `public_key.pem`, or `keystore.p12` in `/cert` folder.

### UI Service

#### Plugin UI Service

id `ltd.hlaeja.plugin.hlaeja-common-plugin.ui-service`

Default setting and tasks for thymeleaf ui services.

#### Plugin UI Service Thymeleaf Minify

id `ltd.hlaeja.plugin.hlaeja-common-plugin.ui-service-thymeleaf.minify`

minimize all templates files during processResources. skips html tags pre, textarea, script, style, and template

## Releasing Plugin

Run release pipeline from `master` branch.

## Publishing plugin

### Publish plugin locally

```shell
./gradlew clean build publishToMavenLocal
```

### Publish plugin to repository

```shell
./gradlew clean build publishAllPublicationsToGitHubPackagesRepository
```

### Global gradle properties

To authenticate with Gradle to access repositories that require authentication, you can set your user and token in the `gradle.properties` file.

Here's how you can do it:

1. Open or create the `gradle.properties` file in your Gradle user home directory:
    - On Unix-like systems (Linux, macOS), this directory is typically `~/.gradle/`.
    - On Windows, this directory is typically `C:\Users\<YourUsername>\.gradle\`.
2. Add the following lines to the `gradle.properties` file:
    ```properties
    repository.user=your_user
    repository.token=your_token_value
    ```
   or use environment variables `REPOSITORY_USER` and `REPOSITORY_TOKEN`
