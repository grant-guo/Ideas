package grant.guo.ideas.eventbus

import grant.guo.ideas.eventbus.inject.SpringConfiguration
import org.springframework.context.annotation.AnnotationConfigApplicationContext

object Main extends App {
  System.setProperty("spring.profiles.active", "production")
  val context = new AnnotationConfigApplicationContext() {{
    this.register(classOf[SpringConfiguration])
    this.refresh()
  }}

}
