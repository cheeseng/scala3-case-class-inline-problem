class Test {

  inline def test(fun: => Any): Any = fun

  test {
    case class Pair[X, Y](
      x: X,
      y: Y,
    )
  }

}