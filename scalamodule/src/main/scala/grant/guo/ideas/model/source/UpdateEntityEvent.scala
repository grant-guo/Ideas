package grant.guo.ideas.model.source

case class UpdateEntityEvent(
                            val id: String,
                            val attr1: Option[Int],
                            val attr2: Option[String]
                            )
