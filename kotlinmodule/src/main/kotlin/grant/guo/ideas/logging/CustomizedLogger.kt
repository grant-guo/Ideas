package grant.guo.ideas.logging

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.Logger
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.slf4j.event.Level

class CustomizedLogger(private val logger: Logger): Logger by logger {

    private val mapper by lazy {
        ObjectMapper().registerKotlinModule()
    }

    fun loggerFunction(msg: Any, level: Level = Level.INFO) {

        getLoggerFunction(level)(
            MarkerFactory.getMarker("CUSTOMIZED").apply { this.add(MarkerFactory.getMarker("chained_marker")) },
            mapper.writeValueAsString(msg)
        )
    }


    private fun getLoggerFunction(level: Level): (Marker, String) -> Unit =
        when(level) {
            Level.INFO -> logger::info
            Level.DEBUG -> logger::debug
            Level.ERROR -> logger::error
            Level.TRACE -> logger::trace
            Level.WARN -> logger::warn
        }
}