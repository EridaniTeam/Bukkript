package org.kotlinmc.bukkript.script.host.compiler

import org.kotlinmc.bukkript.script.definition.ScriptDescription
import org.kotlinmc.bukkript.script.host.cache.CachedScript
import org.kotlinmc.bukkript.script.host.exception.BukkriptCompilationException
import java.io.File

interface BukkriptScriptCompiler {

    suspend fun retrieveDescriptor(scriptFile: File): ScriptDescription?

    @Throws(BukkriptCompilationException::class)
    suspend fun compile(scriptFile: File, description: ScriptDescription): BukkriptCompiledScript

    suspend fun getCachedScript(scriptFile: File): CachedScript?

}