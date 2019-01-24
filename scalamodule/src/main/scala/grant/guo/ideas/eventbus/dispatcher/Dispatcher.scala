package grant.guo.ideas.eventbus.dispatcher

trait Dispatcher {
  def dispatch(event: Any)
}
