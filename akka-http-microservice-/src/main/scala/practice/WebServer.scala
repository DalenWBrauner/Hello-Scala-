import akka.actor.ActorSystem
import akka.http.scaladsl.{Http, server}
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn

case class Color(name:String, red:Int, green:Int, blue:Int) {
  require(0 <= red && red <= 255, "red must be between 0 and 255")
  require(0 <= green && green <= 255, "green must be between 0 and 255")
  require(0 <= blue && blue <= 255, "blue must be between 0 and 255")
}

object WebServer {
  val APP_NAME = "Alkes"

  // Outer Layer
  val routes = {
    pathPrefix(APP_NAME) {
      routingLogic()
    } ~ get {
      getFromResource("public/index.html")
    }
  }

  // Inner Layer
  def routingLogic(): server.Route = {
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Hello, HTTP!</h1>"))
      }
    } ~
      path("simple") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Okay, I think I'm GETting the hang of this!</h1>"))
        } ~
        post {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "I see that POST request...!"))
        } ~
        delete {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "I see that DELETE request...!"))
        }
      } ~ path("square" / IntNumber) { num =>
      get {
        val result = num * num
        complete {
          HttpEntity(ContentTypes.`text/html(UTF-8)`,s"$num * $num = $result")
        }
      }
    } ~ path("concat" / Segment) { num =>
      get {
        val result = num + num
        complete {
          HttpEntity(ContentTypes.`text/html(UTF-8)`,s"$num + $num<br>= $result")
        }
      }
    } ~ path("share" / RemainingPath) { remaining =>
      get {
        complete {
          HttpEntity(ContentTypes.`text/html(UTF-8)`, s"$remaining")
        }
      }
    }
  }

  def main(args: Array[String]) {

    // effectively boilerplate
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    // The Magic
    val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)

    // Console "Controls"
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return

    // Closing Shop
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}