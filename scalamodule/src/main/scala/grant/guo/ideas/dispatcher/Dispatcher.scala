package grant.guo.ideas.dispatcher

trait Dispatcher {
  def dispatch(event: Any)
}
