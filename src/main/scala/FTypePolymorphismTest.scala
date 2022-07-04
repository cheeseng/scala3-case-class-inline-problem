class FTypePolymorphismTest:

  // In this case it is unable to resolve since there is a
  // cyclic reference. It seems that it is not encapsulating the traits
  // If I remove one of the "test" it works fine. But both having an
  // Entity it will not compile

  inline def test(fun: => Any): Any = fun

  test {
    trait Entity[E]:
      def create(): E
      def read(id: Long): Option[E]
      def update(f: E => E): E
      def delete(id: Long): Unit

    class Apple extends Entity[Apple]:
      override def create(): Apple = ???
      override def read(id: Long): Option[Apple] = ???
      override def update(f: Apple => Apple): Apple = ???
      override def delete(id: Long): Unit = ???

    // Issue: We can extend the following in unintended ways
    class FlyingSaucer
    class Orange extends Entity[FlyingSaucer]:
      override def create(): FlyingSaucer = ???
      override def read(id: Long): Option[FlyingSaucer] = ???
      override def update(f: FlyingSaucer => FlyingSaucer): FlyingSaucer = ???
      override def delete(id: Long): Unit = ???
  }

  test {
    trait Entity[E <: Entity[E]]:
      def create(): E
      def read(id: Long): Option[E]
      def update(f: E => E): E
      def delete(id: Long): Unit
    class Apple extends Entity[Apple]:
      override def create(): Apple = ???
      override def read(id: Long): Option[Apple] = ???
      override def update(f: Apple => Apple): Apple = ???
      override def delete(id: Long): Unit = ???

    // Excellent. This now doesn't work
    //    class Orange extends Entity[FlyingSaucer] {
    //      override def create(): FlyingSaucer = ???
    //      override def read(id: Long): Option[FlyingSaucer] = ???
    //      override def update(f: FlyingSaucer => FlyingSaucer): FlyingSaucer = ???
    //      override def delete(id: Long): Unit = ???
    //    }

    class Cantaloupe extends Entity[Cantaloupe]:
      override def create(): Cantaloupe = ???
      override def read(id: Long): Option[Cantaloupe] = ???
      override def update(f: Cantaloupe => Cantaloupe): Cantaloupe = ???
      override def delete(id: Long): Unit = ???
  }
