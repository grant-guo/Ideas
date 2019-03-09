package grant.guo.ideas.logging

import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.contrib.json.classic.JsonLayout
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * this class is used in logback.xml to format the log message into json format.
 *
 * By default, JsonLayout will wrap log message into the following format
 *  "message": " ${log message}"
 */

class LogbackJsonLayout: JsonLayout() {

    var logPayloadDataClassname: String = "kotlin.collections.Map"

    private val mapper by lazy {
        ObjectMapper().registerKotlinModule()
    }

    override protected fun addCustomDataToJsonMap(map: MutableMap<String, Any>, event: ILoggingEvent) {
        val message =
            when( logPayloadDataClassname) {
                "java.util.Map", "kotlin.collections.Map" -> {
                    val typeRef = object : TypeReference<Map<String, String>>() {}
                    mapper.readValue(event.formattedMessage, typeRef)
                }
                else -> {
                    mapper.readValue(event.formattedMessage, Class.forName(logPayloadDataClassname))
                }
            }

        map.replace(FORMATTED_MESSAGE_ATTR_NAME, message)

    }

    override fun doLayout(event: ILoggingEvent): String =

        when(
            Try{
                mapper.readTree(event.formattedMessage)
            }
        ) {
            is Success -> {super.doLayout(event)}
            is Failure -> {event.formattedMessage}
        }

}