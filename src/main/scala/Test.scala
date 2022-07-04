class Test {

  inline def test(fun: => Any): Any = fun

  /* This works as of 3.1.3 */
  test {
    case class Pair[X, Y](
      x: X,
      y: Y,
    )
  }

    /**
     * This does not work as of 3.1.3
     * Self-types are not resolved, this does work though if
     * Moveable and Vehicle are removed from the test context
     */
  test {
     transparent trait Moveable {
         def increaseSpeed(ms: Int): Moveable
         def decreaseSpeed(ms: Int): Moveable
     }

     trait Vehicle { self: Moveable =>
         def make: String
         def model: String
     }

     class Car(val make: String, val model: String, val currentSpeed: Int) extends Vehicle with Moveable {
         override def increaseSpeed(ms: Int):Car = new Car(make, model, currentSpeed + ms)
         override def decreaseSpeed(ms: Int):Car = new Car(make, model, currentSpeed - ms)
     }

     val ford:Car = new Car("Ford", "Fiesta", 110).decreaseSpeed(20)
  }
}
