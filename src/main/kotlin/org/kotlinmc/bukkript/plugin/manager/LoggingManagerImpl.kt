package org.kotlinmc.bukkript.plugin.manager


import org.bukkit.entity.Player
import org.kotlinmc.bukkit.collections.onlinePlayerMapOf
import org.kotlinmc.bukkit.extensions.Log.info
import org.kotlinmc.bukkript.script.definition.api.LogLevel
import org.kotlinmc.bukkript.plugin.BukkriptPlugin
import org.kotlinmc.bukkript.plugin.manager.logging.LogToFileInterceptor
import org.kotlinmc.bukkript.plugin.manager.logging.LogToPlayerInterceptor
import org.kotlinmc.bukkript.plugin.manager.logging.logFormatterInterceptor
import java.io.File

class LoggingManagerImpl(override val plugin: BukkriptPlugin) : LoggingManager {

    private data class Interceptor(
        val priority: Int,
        val interceptor: (scriptName: String, LogLevel, message: String) -> String?,
    ) : Comparable<Interceptor> {
        override fun compareTo(
            other: Interceptor,
        ): Int = other.priority.compareTo(priority)
    }

    private val interceptors = mutableListOf<Interceptor>()

    override fun logScript(scriptName: String, level: LogLevel, message: String) {
        val newMessage = interceptors.sortedDescending().fold(message as String?) { current, interceptor ->
            if (current != null)
                interceptor.interceptor(scriptName, level, current)
            else
                null
        }

        if (newMessage != null)
            info(newMessage)
    }

    override fun intercept(priority: Int, interceptor: (scriptName: String, LogLevel, message: String) -> String?) {
        interceptors.add(Interceptor(priority, interceptor))
    }

    override fun listenLog(player: Player, scriptName: String) {
        logToPlayerInterceptor.players.getOrPut(player) { mutableListOf() }
            .add(scriptName)
    }

    override fun unlistenLog(player: Player, scriptName: String) {
        logToPlayerInterceptor.players[player]
            ?.remove(scriptName)
    }

    override fun isListingLog(player: Player): Boolean {
        return logToPlayerInterceptor.players[player] != null
    }

    val scriptLogFolder = File(plugin.dataFolder, "logs")

    private val logToFileInterceptor = LogToFileInterceptor(scriptLogFolder)
    private val logToPlayerInterceptor by lazy { LogToPlayerInterceptor(onlinePlayerMapOf()) }

    override fun onPluginEnable() {
        intercept(Int.MIN_VALUE, ::logFormatterInterceptor)
        intercept(1, logToFileInterceptor::interceptor)
        intercept(2, logToPlayerInterceptor::interceptor)
    }

}