plugins {
    id("ltd.hlaeja.plugin.hlaeja-core-plugin")
}

tasks {
    withType<Jar>().configureEach {
        manifest.attributes.apply {
            put("Implementation-Title", project.name.split("-").joinToString(" "))
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
