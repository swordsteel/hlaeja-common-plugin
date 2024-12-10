val filesToCopy = listOf(
    "private_key.pem",
    "public_key.pem",
    "keystore.p12",
)

tasks {
    register<Copy>("copyCertificates") {
        group = "hlaeja"
        into("${layout.buildDirectory.get()}/resources/main/cert")
        filesToCopy.filter { file("cert/$it").exists() }
            .forEach { file ->
                from("cert/$file") {
                    include(file)
                }
            }
    }
}
