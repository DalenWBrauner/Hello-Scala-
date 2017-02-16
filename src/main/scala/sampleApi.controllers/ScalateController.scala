package sampleApi.controllers
import org.scalatra.ScalatraServlet
import org.slf4j.{Logger, LoggerFactory}
import scalate.ScalateSupport

//following http://www.scalatra.org/guides/views/scalate.html

class ScalateController extends ScalatraServlet with ScalateSupport {

  get("/") {
    contentType = "text/html"

    layoutTemplate("/WEB-INF/templates/layouts/index.ssp")
  }
}