package sampleApi.controllers
import org.scalatra.ScalatraServlet
import org.slf4j.{Logger, LoggerFactory}
//import scalate.ScalateSupport

//class GreetingController extends ScalatraServlet with ScalateSupport {
class GreetingController extends ScalatraServlet {
  val logger = LoggerFactory.getLogger(getClass)
  logger.info("GreetingController appears operational.")

  get("/") {
    logger.info("Index page contacted.")
    "Hello, Scala(tra)!"
  }

  get("/:name") {
    val name = params.getOrElse("name", "World")
    "Hello, " + name + "!"
    logger.info(s"World $name contacted.")
  }
}