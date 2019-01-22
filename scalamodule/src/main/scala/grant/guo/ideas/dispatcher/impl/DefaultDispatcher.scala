package grant.guo.ideas.dispatcher.impl

import com.google.common.eventbus.EventBus
import grant.guo.ideas.dispatcher.Dispatcher

class DefaultDispatcher(val handlers: List[Any]) extends Dispatcher{

  private lazy val eventBus = getEventBus()

  private def getEventBus(): EventBus = {
    handlers.foldLeft(new EventBus()){(bus, handler) => {
      bus.register(handler)
      bus
    }}
  }

  override def dispatch(event: Any): Unit = {
    eventBus.post(event)
  }
}
