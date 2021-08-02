package org.kotlinmc.bukkript.script.definition.api

import org.kotlinmc.bukkit.dsl.scoreboard.ScoreboardDSLBuilder
import org.kotlinmc.bukkit.dsl.scoreboard.ScoreboardDSLMarker
import org.kotlinmc.bukkit.dsl.scoreboard.scoreboard
import org.kotlinmc.bukkript.script.definition.BukkriptScript


@ScoreboardDSLMarker
inline fun BukkriptScript.scoreboard(
    title: String,
    block: ScoreboardDSLBuilder.() -> Unit,
) = plugin.scoreboard(title, block).apply {
    onDisable {
        dispose()
    }
}