import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by dalenwbrauner on 4/2/17.
  */

object miscScala extends App {
  // += is not an atomic action

  @volatile var totalA = 0

  val text = Future {
    println("I made it this far")
    "na" * 16 + "BATMAN!!!"
  }

  text onSuccess {
    case txt => {
      totalA += txt.count(_ == 'a')
      println("I made it this far 2")
    }
  }

  text onSuccess {
    case txt => {
      totalA += txt.count(_ == 'A')
      println("I made it this far 3")
    }
  }

  text onComplete {
    case txt => {
      println(txt, totalA)
      println("I made it this far 4")
    }
  }

  text onFailure {
    case txt => println("What is happening")
  }
}