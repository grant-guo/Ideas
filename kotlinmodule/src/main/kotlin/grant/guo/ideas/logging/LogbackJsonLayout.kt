package grant.guo.ideas.logging

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.contrib.json.classic.JsonLayout
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * this class is used in logback.xml to format the log message into json format.
 *
 * By default, JsonLayout will wrap log message into the following format
 *  "message": " ${log message}"
 */

class LogbackJsonLayout: JsonLayout() {

    var logPayloadDataClassname: String = ""
    var logPayloadDataKey: String = "nova"

    private val mapper by lazy {
        ObjectMapper().registerKotlinModule()
    }

    private fun getPayloadDataClass(): Class<*> = Class.forName(logPayloadDataClassname)

    override protected fun addCustomDataToJsonMap(map: MutableMap<String, Any>, event: ILoggingEvent) {
        map.put(
            logPayloadDataKey,
            try {
                mapper.readValue(event.formattedMessage, getPayloadDataClass())
            }
            catch (e: Exception){
                event.formattedMessage
            }
        )
    }
}