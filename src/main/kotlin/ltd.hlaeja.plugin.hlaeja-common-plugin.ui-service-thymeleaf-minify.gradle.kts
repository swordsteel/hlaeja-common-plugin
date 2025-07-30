import kotlin.text.RegexOption.IGNORE_CASE

tasks {
    register("minifyTemplates") {
        val srcTemplates = file("src/main/resources/templates")
        val outTemplates = layout.buildDirectory.dir("resources/main/templates")
        val preserveTags = listOf("pre", "textarea", "script", "style", "template")
        inputs.dir(srcTemplates)
        outputs.dir(outTemplates)
        doLast {
            fileTree(srcTemplates).matching { include("**/*.html") }
                .forEach { srcFile ->
                    val preserve = preserveTags.joinToString("|")
                    val preservedBlocks = mutableListOf<String>()
                    Regex("<(?:$preserve)\\b[^>]*>[\\s\\S]*?<\\/\\s*(?:$preserve)>", IGNORE_CASE)
                        .replace(srcFile.readText()) {
                            val id = preservedBlocks.size
                            preservedBlocks += it.value
                            "%%%PRESERVE_BLOCK_$id%%%"
                        }
                        .replace(Regex("<!--([\\s\\S]*?)-->"), "")
                        .replace(Regex("[\\r\\n]+"), "")
                        .replace(Regex("\\s{2,}"), " ")
                        .replace(Regex(">\\s+<"), "><")
                        .replace(Regex("\\s*%%%PRESERVE_BLOCK_(\\d+)%%%\\s*")) {
                            preservedBlocks[it.groupValues[1].toInt()]
                        }
                        .let { stripped ->
                            outTemplates.get().asFile.resolve(srcFile.relativeTo(srcTemplates)).also { file ->
                                file.parentFile.mkdirs()
                                file.writeText(stripped)
                            }
                        }
                }
        }
    }
    named<ProcessResources>("processResources") {
        exclude("templates/**")
        dependsOn(findByPath("minifyTemplates"))
    }
}
