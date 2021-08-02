package org.kotlinmc.bukkript.script.host.loader

import org.kotlinmc.bukkript.script.definition.BukkriptScript
import org.kotlinmc.bukkript.script.host.compiler.BukkriptCompiledScript
import java.io.File
import kotlin.reflect.KClass

data class BukkriptLoadedScript(
    val script: BukkriptScript,
    val kclass: KClass<*>,
    val classLoader: ScriptClassloader,
    val compiledScript: BukkriptCompiledScript,
    val dataFolder: File
)