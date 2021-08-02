package org.kotlinmc.bukkript.plugin.manager.logging

import org.kotlinmc.bukkript.script.definition.api.LogLevel
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LogToFileInterceptor(val logFolder: File) {

    fun interceptor(scriptName: String, level: LogLevel, message: String): String? {
        val date = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())

        File(logFolder, "$scriptName.log")
            .apply {
                parentFile.mkdirs()

                if (!exists()) createNewFile()
            }
            .appendText("$date $message\n")

        return message
    }
}
