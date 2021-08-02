package org.kotlinmc.bukkript.plugin.manager


import kotlinx.coroutines.Job
import org.bukkit.entity.Player
import org.kotlinmc.bukkit.architecture.lifecycle.LifecycleListener
import org.kotlinmc.bukkript.script.definition.api.LogLevel
import org.kotlinmc.bukkript.plugin.BukkriptPlugin
import org.kotlinmc.bukkript.plugin.manager.script.ScriptState
import java.util.concurrent.ConcurrentHashMap

interface ScriptManager : LifecycleListener<BukkriptPlugin> {

    val scripts: ConcurrentHashMap<String, ScriptState>

    fun compile(scriptName: String): Job

    fun load(scriptName: String)

    fun forceLoad(scriptName: String)

    fun isLoaded(scriptName: String): Boolean

    fun unload(scriptName: String)

    fun reload(scriptName: String)

    fun recompile(scriptName: String)

    fun lockLog(player: Player, scriptName: String)

    fun hotRecompile(scriptName: String)

    fun disableHotRecompile(scriptName: String)

    fun isHotRecompileEnable(scriptName: String): Boolean

    fun discoveryAllScripts()

    fun updateLogLevel(scriptName: String, logLevel: LogLevel)
}