package grant.guo.ideas.model.converter

import java.lang.reflect.Method

import grant.guo.ideas.model.target.Entity

class ModelConverterRegistry(val converters: List[Any]) {
  private lazy val toEntityFunctions = getToEntityFunctions()
  private lazy val updateEntityFunctions = getUpdateEntityFunctions()

  private def getToEntityFunctions(): Map[Class[_], (Method, Any)] = {

    def accept(method: Method): Boolean = {
      Option(method.getAnnotation(classOf[ToEntity])) match {
        case Some(_) => method.getParameterCount == 1 && method.getReturnType.equals(classOf[Entity])
        case None => false
      }
    }

    (
      for {
        converter <- converters
        method <- converter.getClass.getDeclaredMethods if(accept(method))
        parameter <- method.getParameterTypes
      } yield {
        (parameter, (method, converter))
      }
    ).toMap
  }

  private def getUpdateEntityFunctions(): Map[Class[_], (Method, Any)] = {
    def accept(method: Method): Boolean = {
      Option(method.getAnnotation(classOf[UpdateEntity])) match {
        case Some(_) => { method.getParameterCount == 2 &&
          method.getParameterTypes(0).equals(classOf[Entity])
          method.getReturnType.equals(classOf[Entity])
        }
        case None => false
      }
    }

    (
      for {
        converter <- converters
        method <- converter.getClass.getDeclaredMethods if(accept(method))
        parameter <- method.getParameterTypes.zipWithIndex if(parameter._2 == 1)
      } yield {
        (parameter._1, (method, converter))
      }
    ).toMap
  }

  def toEntity(event: Any): Entity = {
    toEntityFunctions.get(event.getClass) match {
      case Some(tuple) => {
        tuple._1.invoke(tuple._2, event).asInstanceOf[Entity]
      }
      case None => throw new Exception("")
    }
  }

  def updateEntity(entity: Entity, event: Any): Entity = {
    updateEntityFunctions.get(event.getClass) match {
      case Some(tuple) => {
        tuple._1.invoke(tuple._2, entity, event).asInstanceOf[Entity]
      }
      case None => throw new Exception("")
    }
  }

}
