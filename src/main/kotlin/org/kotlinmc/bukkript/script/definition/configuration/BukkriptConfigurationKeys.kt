package org.kotlinmc.bukkript.script.definition.configuration

import org.kotlinmc.bukkript.script.definition.ScriptDescription
import org.kotlinmc.bukkript.script.definition.api.LogLevel
import kotlin.script.experimental.api.ScriptCompilationConfigurationKeys
import kotlin.script.experimental.util.PropertiesCollection

typealias CompilerKeys = ScriptCompilationConfigurationKeys

val CompilerKeys.info by PropertiesCollection.key<ScriptDescription>(
    ScriptDescription(
        "Unknown",
        "Unknown",
        "None",
        emptySet(),
        LogLevel.INFO,
        emptySet()
    )
)