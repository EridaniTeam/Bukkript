package org.kotlinmc.bukkript.plugin


import org.bukkit.ChatColor
import org.kotlinmc.bukkit.architecture.KotlinPlugin
import org.kotlinmc.bukkit.extensions.plus
import org.kotlinmc.bukkript.plugin.manager.LoggingManagerImpl
import org.kotlinmc.bukkript.plugin.manager.ScriptManagerImpl


class BukkriptPlugin : KotlinPlugin() {
    init {
        try {
            Class.forName("kotlin.script.experimental.jvm.CompiledJvmScriptsCache")
        } catch (e: Exception) {
            LibraryLoader.load(this)
        }
    }

    val loggingManager = lifecycle(100) { LoggingManagerImpl(this) }
    val scriptManager = lifecycle(90) { ScriptManagerImpl(this) }

    override fun onPluginEnable() {
        registerCommands()

        checkServerVersion()
    }

    private fun checkServerVersion() {
        if (server.version.contains("craftbukkit", ignoreCase = true)) {
            repeat(5) {
                error(ChatColor.RED + SERVER_NOT_SUPPORTED_MESSAGE)
            }

            server.pluginManager.disablePlugin(this)

            throw ServerNotSupportedException()
        }
    }
}