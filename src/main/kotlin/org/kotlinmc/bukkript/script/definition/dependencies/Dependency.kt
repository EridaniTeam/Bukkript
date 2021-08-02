package org.kotlinmc.bukkript.script.definition.dependencies

data class Dependency(
    val fqnPackage: String,
    val repositoriesUrl: List<String>,
    val artifacts: List<String>,
)


private const val KOTLINBUKKITAPI_VERSION = "0.0.3"

val KOTLINBUKKITAPI_DEPENDENCY = Dependency(
    "org.koltinmc.bukkit",
    listOf("https://maven.eridani.club/"),
    listOf(
        "club.eridani.bukkit:kotlin-api:$KOTLINBUKKITAPI_VERSION"
    )
)


val SPIGOT_DEPENDENCY = Dependency(
    "org.spigotmc",
    listOf(
        "https://hub.spigotmc.org/nexus/content/repositories/snapshots/",
        "https://oss.sonatype.org/content/repositories/snapshots/"
    ),
    listOf(
        "org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT"
    )
)

val baseDependencies = listOf(
    KOTLINBUKKITAPI_DEPENDENCY,
    SPIGOT_DEPENDENCY
)

val ignoredPluginDependencies = listOf(
    "bukkript",
)