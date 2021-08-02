package org.kotlinmc.bukkript.script.host.cache

import org.kotlinmc.bukkript.script.host.compiler.BukkriptCompiledScript
import java.io.File

data class CachedScript(
    val cacheFile: File,
    val isValid: Boolean,
    val compiled: BukkriptCompiledScript
)