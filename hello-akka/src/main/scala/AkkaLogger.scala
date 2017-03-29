/**
  * Created by dalenwbrauner on 3/29/17.
  */

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }
import scala.concurrent.duration._

// Message Classes

case object LogRequest
case class LogResponse(message: String)
case class LogThis(who:Int, what: String)

// Actor Classes

class Logger extends Actor {
  var contents = ""

  def receive = {
    case LogRequest      => sender ! LogResponse(contents)
    case LogThis(who, what) => {
      val logDate = java.time.LocalDate.now
      val logTime = java.time.LocalTime.now
      contents += s"[ID:$who | $logDate | $logTime]: $what\n"
    }
  }
}

object AkkaLogger extends App {

  // Actor Instantiation
  val system = ActorSystem("LogSystem")
  val logger = system.actorOf(Props[Logger], "logger")

  // Inbox Instantiation
  val inbox = Inbox.create(system)

  // Let's write Akka a letter...
  val ourID = 0 // We would do this very differently if we were using real actors, but
  logger.tell(LogThis(ourID,"Dear Akka,"), ActorRef.noSender)
  logger.tell(LogThis(ourID,"Your tutorial had some pretty bad variable names,"), ActorRef.noSender)
  logger.tell(LogThis(ourID,"but it still helped me make sense of everything in the end."), ActorRef.noSender)
  logger.tell(LogThis(ourID,"That activator thing is pretty interesting!"), ActorRef.noSender)
  logger.tell(LogThis(ourID,"It's definitely eerie that it's automatically compiling everything"), ActorRef.noSender)
  logger.tell(LogThis(ourID,"I write the minute I hit save and then running it, though."), ActorRef.noSender)
  logger.tell(LogThis(ourID,"But I guess it saves me from having to type 'sbt run' in the console,"), ActorRef.noSender)
  logger.tell(LogThis(ourID,"so thank you."), ActorRef.noSender)
  logger.tell(LogThis(ourID,"\t\tSincere thanks,"), ActorRef.noSender)
  logger.tell(LogThis(ourID,"\t\tDalen."), ActorRef.noSender)
  inbox.send(logger, LogRequest)

  // ...and wait a few seconds for the reply.
  val LogResponse(message1) = inbox.receive(5.seconds)
  println(message1)
}