import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import com.bmuschko.gradle.docker.tasks.network.DockerCreateNetwork
import com.bmuschko.gradle.docker.tasks.network.DockerInspectNetwork
import com.bmuschko.gradle.docker.tasks.network.DockerRemoveNetwork
import java.lang.System.getenv

plugins {
    id("com.bmuschko.docker-spring-boot-application")
}

fun configuration(
    property: String,
    environment: String,
    default: String,
): String = project.findProperty(property)?.toString() ?: getenv(environment) ?: default

fun configurationPorts(
    property: String,
    environment: String,
): List<String> = configuration(property, environment, "8080").split(',')

fun exposeDockerPorts(): List<Int> = configurationPorts("docker.port.expose", "DOCKER_PORT_EXPOSE")
    .mapNotNull { it.toIntOrNull() }

fun exposeContainerPorts(): List<String> = configurationPorts("container.port.host", "CONTAINER_PORT_HOST")
    .zip(configurationPorts("container.port.expose", "CONTAINER_PORT_EXPOSE"))
    .map { (containerPort, exposedPort) -> "$containerPort:$exposedPort" }

docker.springBootApplication {
    baseImage.set("eclipse-temurin:17-jre-alpine")
    ports.set(exposeDockerPorts())
    images.set(listOf("${project.name}:${project.version}"))
}

tasks {
    register("containerCreate", DockerCreateContainer::class) {
        group = "hlaeja"
        targetImageId("${project.name}:${project.version}")
        containerName.set(project.name)
        hostConfig.autoRemove.set(true)
        hostConfig.network.set(configuration("container.network", "CONTAINER_NETWORK", "develop"))
        hostConfig.portBindings.set(exposeContainerPorts())
        withEnvVar("SPRING_PROFILES_ACTIVE", configuration("container.profiles", "CONTAINER_PROFILES", "docker"))
    }
    register("containerStart", DockerStartContainer::class) {
        group = "hlaeja"
        dependsOn(findByPath("containerCreate"))
        targetContainerId(project.name)
    }
    register("containerStop", DockerStopContainer::class) {
        group = "hlaeja"
        targetContainerId(project.name)
    }
    register("containerNetworkCheck", DockerInspectNetwork::class) {
        group = "hlaeja"
        targetNetworkId(configuration("container.network", "CONTAINER_NETWORK", "develop"))
        onError { println("Network does not exist.") }
    }
    register("containerNetworkCreate", DockerCreateNetwork::class) {
        group = "hlaeja"
        networkName.set(configuration("container.network", "CONTAINER_NETWORK", "develop"))
    }
    register("containerNetworkRemove", DockerRemoveNetwork::class) {
        group = "hlaeja"
        targetNetworkId(configuration("container.network", "CONTAINER_NETWORK", "develop"))
    }
}
