package sampleApi.controllers
import org.scalatra.ScalatraServlet
import org.slf4j.{Logger, LoggerFactory}
//import scalate.ScalateSupport

//class GreetingController extends ScalatraServlet with ScalateSupport {
class GreetingController extends ScalatraServlet {
  val logger = LoggerFactory.getLogger(getClass)
  logger.info("GreetingController appears operational.")

  get("/?") {
    logger.info("Index page contacted.")
    <html lang="en">
      <body>
        Hello, Scala(tra)!
        <a href="/horn">Horn Demo Pages</a>
        <br></br>
        Try typing your name after the last slash in the URL on various pages.
      </body>
    </html>
  }

  get("/:name") {
    val name = params.getOrElse("name", "World")
    <html lang="en">
      <body>
        Hello, {name}!
        <a href="/horn">Horn Demo Pages</a>
        <br></br>
        Try typing your name after the last slash in the URL on various pages.
      </body>
    </html>
    logger.info(s"World $name contacted.")
  }

  notFound {
    <h1>404</h1>
    <br>You definitely appear to be lost.</br>
  }
}