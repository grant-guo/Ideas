package grant.guo.ideas.handlers.impl

import com.google.common.eventbus.Subscribe
import grant.guo.ideas.environment.Environment
import grant.guo.ideas.handlers.EventHandler

@EventHandler
class EventHandlerTwo(env: Environment) extends BaseEventHandler(env) {

  @Subscribe
  def handle(event: String): Unit = {
    println(s"in EventHandlerOne, the event is a String ${event}")
  }
}
