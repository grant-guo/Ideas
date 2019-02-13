package grant.guo.ideas.logging

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Layout
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase

/**
 * ConsoleAppender doesn't accept a layout as parameter. If the users don't provide any encoder class,
 * the default encoder will be PatternLayoutEncoder which extends PatternLayoutEncoderBase.
 *
 * In PatternLayoutEncoderBase, function setLayout throws an exception directly,
 * so here the new encoder is derived from PatternLayoutEncoderBase directly and override the setLayout
 *
 * We could pass in a customized Layout implementation to format the log message
 */
class DefaultAppenderEncoder: PatternLayoutEncoderBase<ILoggingEvent>() {

    override fun setLayout(layout: Layout<ILoggingEvent>) {
        this.layout = layout
    }

}