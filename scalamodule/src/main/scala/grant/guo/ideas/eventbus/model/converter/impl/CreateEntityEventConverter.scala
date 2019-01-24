package grant.guo.ideas.eventbus.model.converter.impl

import grant.guo.ideas.eventbus.model.converter.{ModelConverter, ToEntity}
import grant.guo.ideas.eventbus.model.source.CreateEntityEvent
import grant.guo.ideas.eventbus.model.target.Entity

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
