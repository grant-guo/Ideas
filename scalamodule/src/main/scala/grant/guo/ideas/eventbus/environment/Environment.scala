package grant.guo.ideas.eventbus.environment

trait Environment {
  def getVariable(key: String): Option[String]
}