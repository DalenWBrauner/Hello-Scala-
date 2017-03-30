import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

import scala.concurrent.duration._

class AkkaLoggerSpec(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender
  with Matchers
  with FlatSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("AkkaLoggerSpec"))

  override def afterAll: Unit = {
    system.shutdown()
    system.awaitTermination(10.seconds)
  }

  "An AkkaLogger" should "be able to log text" in {
    val logger = TestActorRef(Props[Logger])
    val arbitraryID = 0
    logger ! LogThis(arbitraryID,"testkit")
    logger.underlyingActor.asInstanceOf[Logger].contents should include("testkit")
  }

  it should "be able to receive the log" in {
    val logger = system.actorOf(Props[Logger], "logger")
    val arbitraryID = 0
    logger ! LogThis(arbitraryID,"testkit")
    logger ! LogRequest
    expectMsgType[LogResponse].message.toString should include("testkit")
  }

  it should "be able to clear the log" in {
    val logger = system.actorOf(Props[Logger], "logger2")
    val arbitraryID = 0

    // Check for testkit only
    logger ! LogThis(arbitraryID,"testkit")
    logger ! LogRequest
    expectMsgType[LogResponse].message.toString should include("testkit")
    logger ! LogRequest
    expectMsgType[LogResponse].message.toString should not include("something else")

    // Check that testkit is gone
    logger ! LogClear
    logger ! LogThis(arbitraryID,"something else")
    logger ! LogRequest
    expectMsgType[LogResponse].message.toString should not include("testkit")
    logger ! LogRequest
    expectMsgType[LogResponse].message.toString should include("something else")
  }
}
