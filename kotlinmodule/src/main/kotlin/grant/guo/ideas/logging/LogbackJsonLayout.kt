package grant.guo.ideas.logging

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.contrib.json.classic.JsonLayout
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.MDC

/**
 * this class is used in logback.xml to format the log message into json format.
 *
 * By default, JsonLayout will wrap log message into the following format
 *  "message": " ${log message}"
 */

class LogbackJsonLayout: JsonLayout() {

    private val mapper by lazy {
        ObjectMapper().registerKotlinModule()
    }

    override protected fun addCustomDataToJsonMap(map: MutableMap<String, Any>, event: ILoggingEvent) {
        val typeRef = object : TypeReference<Map<String, Any>>() {}
        val contextMap = mutableMapOf<String, Any>("name" to event.loggerContextVO.name)

        MDC.getCopyOfContextMap()?.forEach{ k, v ->
            try {
                contextMap.put(
                    k,
                    mapper.readValue<Map<String, String>>(
                        v,
                        typeRef
                    )
                )
            }
            catch (e: Exception) {
                contextMap.put(k, v)
            }

        }

        map.put(
            CONTEXT_ATTR_NAME,
            contextMap
        )

        try {
            val jsonNode = mapper.readTree(event.message)
            map.put(FORMATTED_MESSAGE_ATTR_NAME, jsonNode)
        }
        catch (exception: Exception){

        }
        finally {
            if(event.marker != null) {
                map.put(
                    "marker",
                    event.marker.name
                )
            }
        }
    }


}