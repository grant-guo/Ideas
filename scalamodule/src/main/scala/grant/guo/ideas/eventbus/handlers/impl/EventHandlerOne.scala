package grant.guo.ideas.eventbus.handlers.impl

import com.google.common.eventbus.Subscribe
import grant.guo.ideas.eventbus.environment.Environment
import grant.guo.ideas.eventbus.handlers.EventHandler

@EventHandler
class EventHandlerOne(env: Environment) extends BaseEventHandler(env) {

  @Subscribe
  def handle(event: Int): Unit = {
    println(s"in EventHandlerOne, the event is an integer ${event}")
  }
}
