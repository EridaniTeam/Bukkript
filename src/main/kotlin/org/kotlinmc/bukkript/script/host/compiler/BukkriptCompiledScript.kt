package org.kotlinmc.bukkript.script.host.compiler

import org.kotlinmc.bukkript.script.definition.ScriptDescription
import kotlin.script.experimental.host.FileScriptSource
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript

data class BukkriptCompiledScript(
    val scriptName: String,
    val source: FileScriptSource,
    val compiled: KJvmCompiledScript,
    val description: ScriptDescription
)