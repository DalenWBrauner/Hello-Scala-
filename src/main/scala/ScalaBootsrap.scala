import javax.servlet.ServletContext
import org.scalatra.LifeCycle

import sampleApi.controllers._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    // Mount servlets.
    context.mount(new HornController, "/horn/*")
    context.mount(new GreetingController, "/*")
    context.mount(new TwirlController, "/Twirl/*")
  }
}