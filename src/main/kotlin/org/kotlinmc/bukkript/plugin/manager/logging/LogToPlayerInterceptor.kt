package org.kotlinmc.bukkript.plugin.manager.logging


import org.kotlinmc.bukkit.collections.OnlinePlayerMap
import org.kotlinmc.bukkit.extensions.msg
import org.kotlinmc.bukkript.script.definition.api.LogLevel

class LogToPlayerInterceptor(
    val players: OnlinePlayerMap<MutableList<String>>,
) {
    fun interceptor(scriptName: String, level: LogLevel, message: String): String? {
        for ((player, scripts) in players)
            if (scriptName in scripts)
                player.msg(message)

        return message
    }
}