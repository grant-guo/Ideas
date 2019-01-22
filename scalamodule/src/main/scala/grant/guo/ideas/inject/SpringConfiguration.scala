package grant.guo.ideas.inject

import grant.guo.ideas.environment.Environment
import grant.guo.ideas.environment.impl.DefaultJvmEnvironment
import grant.guo.ideas.handlers.EventHandler
import grant.guo.ideas.model.converter.{ModelConverter, ModelConverterRegistry}
import org.reflections.Reflections
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.{Bean, Configuration, Profile, Scope}

import scala.collection.convert.{WrapAsJava, WrapAsScala}

@Configuration
class SpringConfiguration {
  @Bean(name = Array("env"))
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  @Profile("production")
  def getEnv(): Environment = new DefaultJvmEnvironment()


  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  def getEventHandlers(@Qualifier("env") env: Environment): List[Any] = {
    WrapAsScala.asScalaSet( (new Reflections("grant.guo.ideas.handlers.impl")).getTypesAnnotatedWith(classOf[EventHandler]) )
      .map(clazz => {
        clazz.getConstructor(classOf[Environment]).newInstance(env)
      }).toList
  }

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  def getModelConverterRegistry(): ModelConverterRegistry = {
    new ModelConverterRegistry(
      WrapAsScala.asScalaSet((new Reflections("grant.guo.ideas.model.converter.impl")).getTypesAnnotatedWith(classOf[ModelConverter]))
        .map(clazz => {
          clazz.newInstance()
        }).toList
    )
  }
}
