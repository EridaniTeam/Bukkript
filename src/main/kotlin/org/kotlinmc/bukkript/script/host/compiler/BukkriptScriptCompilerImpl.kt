package org.kotlinmc.bukkript.script.host.compiler


import org.kotlinmc.bukkript.script.definition.BukkriptScript
import org.kotlinmc.bukkript.script.definition.ScriptDescription
import org.kotlinmc.bukkript.script.definition.bukkriptNameRelative
import org.kotlinmc.bukkript.script.definition.configuration.info
import org.kotlinmc.bukkript.script.host.cache.CachedScript
import org.kotlinmc.bukkript.script.host.cache.FileBasedScriptCache
import org.kotlinmc.bukkript.script.host.exception.BukkriptCompilationException
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.FileScriptSource
import kotlin.script.experimental.host.ScriptingHostConfiguration
import kotlin.script.experimental.host.with
import kotlin.script.experimental.jvm.compilationCache
import kotlin.script.experimental.jvm.defaultJvmScriptingHostConfiguration
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.JvmScriptCompiler
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

class BukkriptScriptCompilerImpl(
    val scriptDir: File,
    val cacheDir: File,
) : BukkriptScriptCompiler {

    override suspend fun retrieveDescriptor(scriptFile: File): ScriptDescription? {
        var scriptDescriptionLoaded: ScriptDescription? = null


        val customConfiguration =
            createJvmCompilationConfigurationFromTemplate<BukkriptScript>().with {

                refineConfiguration {
                    beforeCompiling { context ->
                        val info = context.compilationConfiguration[ScriptCompilationConfiguration.info]!!

                        scriptDescriptionLoaded = info

                        return@beforeCompiling ResultWithDiagnostics.Failure()
                    }
                }
            }

        val source = FileScriptSource(scriptFile)

        runCatching {
            compile(source, customConfiguration)
        }.onFailure {
            if (scriptDescriptionLoaded == null)
                throw it
        }

        return scriptDescriptionLoaded
    }


    override suspend fun compile(scriptFile: File, description: ScriptDescription): BukkriptCompiledScript {
        val source = FileScriptSource(scriptFile)

        val cache = FileBasedScriptCache(scriptDir, cacheDir, description)

        val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<BukkriptScript>()

        val hostConfiguration = defaultJvmScriptingHostConfiguration.with {
            jvm {
                compilationCache(cache)
            }
        }

        return runCatching {
            val compiledScript = compile(source, compilationConfiguration, hostConfiguration)
                .valueOrThrow() as KJvmCompiledScript

            BukkriptCompiledScript(
                scriptFile.bukkriptNameRelative(scriptDir),
                source,
                compiledScript,
                description
            )
        }.getOrElse {
            throw BukkriptCompilationException(it)
        }
    }

    override suspend fun getCachedScript(scriptFile: File): CachedScript? {
        val scriptCache = FileBasedScriptCache(scriptDir, cacheDir, null)

        val source = FileScriptSource(scriptFile)

        return scriptCache.findCacheScript(source)
    }

    private suspend fun compile(
        source: FileScriptSource,
        configuration: ScriptCompilationConfiguration,
        hostConfiguration: ScriptingHostConfiguration = defaultJvmScriptingHostConfiguration,
    ): ResultWithDiagnostics<CompiledScript> {
        val compiler = JvmScriptCompiler(hostConfiguration)

        return compiler(source, configuration)
    }

}