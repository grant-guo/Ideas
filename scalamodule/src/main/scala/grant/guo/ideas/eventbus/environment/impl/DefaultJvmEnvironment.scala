package grant.guo.ideas.eventbus.environment.impl

import grant.guo.ideas.eventbus.environment.Environment

class DefaultJvmEnvironment extends Environment{

  private lazy val defaults = getDefaults()

  private def getDefaults(): Map[String, String] = {
    ???
  }

  override def getVariable(key: String): Option[String] = {
    Option(System.getenv(key)) match {
      case Some(value) =>{
        Some(value)
      }
      case None => {
        defaults.get(key)
      }
    }
  }
}
