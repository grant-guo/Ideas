package grant.guo.ideas.seq

object ListDiff extends App {

  val list1 = List(1, 2, 3, 4, 5)
  val list2 = List(4, 5, 6)

  val diff1 = list1.diff(list2)

  val diff2 = list1.filterNot(list2.toSet)

  /*
    why filterNot(...) can accept a Set? In Set class, there is an 'apply' method
   */

  diff1.foreach( println _)
  diff2.foreach( println _)


}
