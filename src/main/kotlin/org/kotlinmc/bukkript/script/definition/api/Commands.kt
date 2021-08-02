package org.kotlinmc.bukkript.script.definition.api

import org.bukkit.command.CommandSender
import org.kotlinmc.bukkit.dsl.command.*
import org.kotlinmc.bukkit.extensions.unregister
import org.kotlinmc.bukkript.script.definition.BukkriptScript

fun BukkriptScript.simpleCommand(
    name: String,
    vararg aliases: String = arrayOf(),
    description: String = "",
    block: ExecutorBlock<CommandSender>,
) = plugin.simpleCommand(name, *aliases, description = description, block = block).apply {
    unregisterOnDisable(this)
}

fun BukkriptScript.command(
    name: String,
    vararg aliases: String = arrayOf(),
    block: CommandBuilderBlock,
) = plugin.command(name, *aliases, block = block).apply {
    unregisterOnDisable(this)
}


fun BukkriptScript.unregisterOnDisable(it: CommandDSL) {
    onDisable {
        it.job.cancel()
        it.unregister()
    }
}