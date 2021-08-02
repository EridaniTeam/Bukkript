package org.kotlinmc.bukkript.script.definition

import org.kotlinmc.bukkript.script.definition.api.LogLevel

data class ScriptDescription(
    val version: String,
    val author: String,
    val website: String,
    val pluginDependencies: Set<String>,
    var logLevel: LogLevel, // Mutable for tooling!
    val dependenciesFiles: Set<String>
) : java.io.Serializable