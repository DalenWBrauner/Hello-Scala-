package sampleApi.controllers
import org.scalatra.ScalatraServlet
import org.scalatest.FunSuite

class GreetingControllerTests extends ScalatraServlet with FunSuite {
  addServlet(classOf[GreetingController], "/*")

  test("get /") {
    get("/") {
      body should include("Hello, Scala(tra)!")
    }
  }
}
