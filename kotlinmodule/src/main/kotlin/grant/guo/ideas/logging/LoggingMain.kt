package grant.guo.ideas.logging

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {

    val logger = LoggerFactory.getLogger(SampleLogEntity::class.java)

    val mapper = ObjectMapper().registerKotlinModule()

    logger.info(
        mapper.writeValueAsString(mapOf(
            "abc" to "123",
            "efg" to "456"
        ))
    )

    logger.info(
        mapper.writeValueAsString(
            SampleLogEntity(
                id = "id1",
                timestamp = 1234567,
                module = "logging",
                message = "test"
            )
        )
    )

    logger.info("this is not json")

}