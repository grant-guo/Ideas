package grant.guo.ideas.model.converter.impl

import grant.guo.ideas.model.converter.{ModelConverter, ToEntity}
import grant.guo.ideas.model.source.CreateEntityEvent
import grant.guo.ideas.model.target.Entity

@ModelConverter
class CreateEntityEventConverter {

  @ToEntity
  def toEntity(event: CreateEntityEvent): Entity = {
    Entity(
      id = event.id,
      field1 = event.attr2,
      field2 = event.attr1
    )
  }
}
