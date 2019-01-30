package grant.guo.ideas.dsl.number


/**
  *
  * 1. We have created some case classes to represent our data
  * 2. We have define a trait with the methods we’d like our case classes to implement
  * 3. We have provided an implementation of the above trait for each of our case classes
  *
  * The Amount trait is called a type class and the implementations (EURIsAmount and GBPIsAmount) are called a type class instances
  *
  */
object NumberDSL extends App {
  case class GBP(value: BigDecimal)
  case class EUR(value: BigDecimal)
  case class Percent(value: BigDecimal)

  //  trait Amount[A] {
  //    def plus(a: A, b:A):A
  //    def times(a: A, b: BigDecimal): A
  //
  //    class Ops(a: A) {
  //      def +(b: A): A = plus(a, b)
  //      def -(b: A): A = plus(a, times(b, -1))
  //      def *(b: BigDecimal): A = times(a, b)
  //      def /(b: BigDecimal): A = times(a, BigDecimal(1) / b)
  //    }
  //  }

  trait Amount[A] {
    def plus(a: A, b: A): A
    def times(a: A, b: BigDecimal): A
    class Ops(a: A) {
      def ++(b: A) = plus(a, b) // i don't why the single '+' doesn't work, always notify "need a string as the input parameter"
      def -(b: A) = plus(a, times(b, -1))
      def *[B](b: B)(implicit constant: Constant[B]): A =
        times(a, constant.underlying(b))
      def /[B](b: B)(implicit constant: Constant[B]): A =
        times(a, BigDecimal(1) / constant.underlying(b))
    }
  }

  trait Constant[A] {
    def underlying(a: A): BigDecimal
    class Ops(a: A) {
      def *[B](b: B)(implicit amount: Amount[B]) = amount.times(b, underlying(a))
    }
  }

  object GBP {
    implicit class GBPFromInt(value: Int) {
      def GBP = new GBP(value)
    }
    implicit object GBPisAmount extends Amount[GBP] {
      override def plus(a: GBP, b: GBP): GBP = GBP(a.value + b.value)

      override def times(a: GBP, b: BigDecimal): GBP = GBP(a.value * b)
    }
    implicit def ops(a: GBP) = new GBPisAmount.Ops(a)
  }

  object EUR {
    implicit class EURFromInt(value: Int) {
      def EUR = new EUR(value)
    }
    implicit object EURIsAmount extends Amount[EUR] {
      def plus(a: EUR, b: EUR): EUR = EUR(a.value + b.value)
      def times(a: EUR, b: BigDecimal): EUR = EUR(a.value * b)
    }
    implicit def ops(a: EUR) = new EURIsAmount.Ops(a)
  }

  object Percent {
    implicit class PercentFromInt(value: Int) {
      def percent = new Percent(value)
    }

    implicit object PercentIsConstant extends Constant[Percent] {
      override def underlying(a: Percent): BigDecimal = a.value / 100
    }
    implicit def percentOps(a: Percent) = new PercentIsConstant.Ops(a)
  }

  implicit object IntIsConstant extends Constant[Int] {
    override def underlying(a: Int): BigDecimal = BigDecimal(a)
  }
  implicit def intConstantOps(b: Int) = new IntIsConstant.Ops(b)

  import GBP._
  import EUR._
  import Percent._

  println(10.GBP)
  println(10.EUR)

  println(10.GBP ++ 20.GBP)
  println(100.EUR - 50.EUR)
  println(3.GBP * 5)
  println(2 * 5.EUR)
  println(1000.EUR * 5.percent)
  println(5.percent * 60.EUR)
}
