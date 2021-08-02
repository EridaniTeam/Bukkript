package org.kotlinmc.bukkript.script.definition

import org.kotlinmc.bukkit.architecture.KotlinPlugin
import org.kotlinmc.bukkit.dsl.command.CommandBuilderBlock
import org.kotlinmc.bukkit.dsl.command.command
import org.kotlinmc.bukkript.script.definition.api.LogLevel
import org.kotlinmc.bukkript.script.definition.api.unregisterOnDisable
import org.kotlinmc.bukkript.script.definition.configuration.BukkriptScriptCompilationConfiguration
import java.io.File
import kotlin.script.experimental.annotations.KotlinScript

const val BUKKRIPT_EXTENSION = "bk.kts"

typealias RemoveRegistryFunction = () -> Unit

@KotlinScript(
    displayName = "Bukkript script",
    fileExtension = BUKKRIPT_EXTENSION,
    compilationConfiguration = BukkriptScriptCompilationConfiguration::class
)
abstract class BukkriptScript(
    val plugin: KotlinPlugin,
    val description: ScriptDescription,
    val dataFolder: File,
    val scriptName: String,
    val log: (LogLevel, message: String) -> Unit,
) {

    private val _onDisableListeners = mutableListOf<() -> Unit>()
    val onDisableListeners: List<() -> Unit> = _onDisableListeners

    fun onDisable(callback: () -> Unit): RemoveRegistryFunction {
        _onDisableListeners.add(callback)

        return { _onDisableListeners.remove(callback) }
    }

    infix operator fun String.invoke(block: CommandBuilderBlock) = plugin.command(this, block = block).apply {
        unregisterOnDisable(this)
    }
}