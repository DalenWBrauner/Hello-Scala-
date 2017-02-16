package sampleApi.controllers
import org.scalatra._
import org.slf4j.{Logger, LoggerFactory}

class TwirlController extends ScalatraServlet {
  val logger = LoggerFactory.getLogger(getClass)
  logger.info("TwirlController appears operational.")

  get("/?") {
    html.index.render(new java.util.Date)
  }

}