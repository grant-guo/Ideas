package grant.guo.ideas.environment

trait Environment {
  def getVariable(key: String): Option[String]
}