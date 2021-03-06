package grant.guo.ideas.eventbus.inject

import grant.guo.ideas.eventbus.environment.Environment
import grant.guo.ideas.eventbus.environment.impl.DefaultJvmEnvironment
import grant.guo.ideas.eventbus.handlers.EventHandler
import grant.guo.ideas.eventbus.model.converter.{ModelConverter, ModelConverterRegistry}
import org.reflections.Reflections
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.{Bean, Configuration, Profile, Scope}

import scala.collection.convert.{WrapAsJava, WrapAsScala}

@Configuration
class SpringConfiguration {
  @Bean(name = Array("env"))
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  @Profile(Array("production"))
  def getEnv(): Environment = new DefaultJvmEnvironment()


  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  def getEventHandlers(@Qualifier("env") env: Environment): List[Any] = {
    WrapAsScala.asScalaSet( (new Reflections("grant.guo.ideas.eventbus.handlers.impl")).getTypesAnnotatedWith(classOf[EventHandler]) )
      .map(clazz => {
        clazz.getConstructor(classOf[Environment]).newInstance(env)
      }).toList
  }

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  def getModelConverterRegistry(): ModelConverterRegistry = {
    new ModelConverterRegistry(
      WrapAsScala.asScalaSet((new Reflections("grant.guo.ideas.eventbus.model.converter.impl")).getTypesAnnotatedWith(classOf[ModelConverter]))
        .map(clazz => {
          clazz.newInstance()
        }).toList
    )
  }
}
