package de.jet.jvm.application.runtime

import de.jet.jvm.application.configuration.JetApp
import de.jet.jvm.application.configuration.JetAppConfigController
import de.jet.jvm.application.configuration.JetAppConfigModule
import de.jet.jvm.application.extension.AppExtension
import de.jet.jvm.application.tag.Version
import de.jet.jvm.application.tag.version
import de.jet.jvm.extension.pathAsFileFromRuntime
import de.jet.jvm.tool.timing.calendar.Calendar
import java.io.File

class JetAppRuntime(override val identity: String, override val version: Version = 1.0.version) :
	JetApp(identity, version) {

	private val runningExtensions = mutableListOf<AppExtension<*, *>>()

	private lateinit var module: JetAppConfigModule

	internal fun init() {
		JetAppConfigController.apply {
			module = JetAppConfigModule.autoGenerateFromApp(this@JetAppRuntime)
			addApp(module)
			(module.appFileFolderPath + "info.jetRun").pathAsFileFromRuntime().apply {
				if (!exists()) {
					toPath().parent.toFile().mkdirs()
					createNewFile()
					writeText("installed='${Calendar.now().javaDate}'")
				}
			}
		}
	}

	val appFolder: File by lazy {
		return@lazy JetAppConfigController.getApp(this)?.appFileFolderPath.let { folderPath ->
			if (folderPath != null) {
				return@let folderPath.pathAsFileFromRuntime()
			} else {
				return@let JetAppConfigModule.autoGenerateFromApp(this).let {
					JetAppConfigController.addApp(it)
					it.appFileFolderPath.pathAsFileFromRuntime()
				}
			}
		}
	}

	fun getAppFile(path: String) = File(appFolder.path + "/" + path).apply {
		File(path.split("/").dropLast(1).joinToString("/")).mkdirs()
	}.toPath()

	fun <RUNTIME, ACCESSOR_OUT, T : AppExtension<RUNTIME, ACCESSOR_OUT>> attach(
		extension: T,
		runtime: (RUNTIME) -> ACCESSOR_OUT
	) {
		if (extension.parallelRunAllowed || runningExtensions.all { it.identity != extension.identity }) {
			runningExtensions.add(extension)
			extension.runtimeAccessor(runtime)
		} else
			throw IllegalStateException("The extension '${extension.identity}' is already running and is not allowed to run parallel to itself!")
	}

	fun <RUNTIME, ACCESSOR_OUT, T : AppExtension<RUNTIME, ACCESSOR_OUT>> attachWith(
		extension: T,
		runtime: RUNTIME.() -> ACCESSOR_OUT
	) =
		attach(extension, runtime)

}