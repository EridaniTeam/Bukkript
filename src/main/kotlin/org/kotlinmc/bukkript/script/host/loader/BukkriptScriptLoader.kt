package org.kotlinmc.bukkript.script.host.loader

import org.kotlinmc.bukkript.script.host.compiler.BukkriptCompiledScript
import org.kotlinmc.bukkript.script.host.exception.BukkriptLoadException

typealias ScriptPath = String

interface BukkriptScriptLoader {
    @Throws(BukkriptLoadException::class)
    suspend fun load(compiledScript: BukkriptCompiledScript): BukkriptLoadedScript
}