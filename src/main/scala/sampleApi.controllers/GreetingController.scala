package sampleApi.controllers
import org.scalatra.ScalatraServlet
//import scalate.ScalateSupport

//class GreetingController extends ScalatraServlet with ScalateSupport {
class GreetingController extends ScalatraServlet {
  get("/") {
    "Hello, Scala(tra)!"
  }

  get("/:name") {
    val name = params.getOrElse("name", "World")
    "Hello, " + name + "!"
  }
}