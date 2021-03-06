package grant.guo.ideas.eventbus.model.converter.impl

import grant.guo.ideas.eventbus.model.converter.{ModelConverter, UpdateEntity}
import grant.guo.ideas.eventbus.model.source.UpdateEntityEvent
import grant.guo.ideas.eventbus.model.target.Entity

@ModelConverter
class UpdateEntityEventConverter {

  @UpdateEntity
  def updateEntity(entity: Entity, event: UpdateEntityEvent): Entity = {
    entity.copy(
      field1 = event.attr2 match {
        case Some(v) => v
        case None => entity.field1
      },
      field2 = event.attr1 match {
        case Some(v) => v
        case None => entity.field2
      }
    )
  }
}
