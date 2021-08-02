package org.kotlinmc.bukkript.plugin.manager


import org.bukkit.entity.Player
import org.kotlinmc.bukkit.architecture.lifecycle.LifecycleListener
import org.kotlinmc.bukkript.script.definition.api.LogLevel
import org.kotlinmc.bukkript.plugin.BukkriptPlugin

interface LoggingManager : LifecycleListener<BukkriptPlugin> {
    fun logScript(scriptName: String, level: LogLevel, message: String)

    /**
     * [interceptor] returns null if is to disable the logging
     */
    fun intercept(priority: Int, interceptor: (scriptName: String, LogLevel, message: String) -> String?)

    /**
     * Make the player receive the messages directly in their chat.
     */
    fun listenLog(player: Player, scriptName: String)

    fun unlistenLog(player: Player, scriptName: String)

    fun isListingLog(player: Player): Boolean
}