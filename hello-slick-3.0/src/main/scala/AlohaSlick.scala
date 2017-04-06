/**
  * Created by dalenwbrauner on 4/3/17.
  */

import scala.concurrent.{Await, Awaitable, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import slick.driver.H2Driver.api._

object AlohaSlick extends App {
  val WeAreBlocking = true

  /** Simplifies if + await statements **/
  def waitMaybe(waitForMe:Awaitable[_]) = if (WeAreBlocking) Await.result(waitForMe,Duration.Inf)

  /** Timestamp format for database entry **/
  def timestamp(): String = {
    val logDate = java.time.LocalDate.now
    val logTime = java.time.LocalTime.now
    s"$logDate @ $logTime"
  }

  /** Format for printing database contents **/
  def printEach(sequence:Seq[(Int, Int, Int, String, String, String, String)], label:String): Unit = {
    sequence.foreach {
      case (id, userid, channelid, timestamp, filename, extension, content) =>
        println(label + s" $id | $userid | $channelid | $timestamp | $filename.$extension : $content")
    }
  }

  // Instantiate Database Connection
  val db = Database.forConfig("h2mem1")
  println("Connected to Database!")

  try {
    // Connect with a table
    val files: TableQuery[Files] = TableQuery[Files]

    // Kickstart with dummy data
    val justAMoment = db.run(DBIO.seq(
      files.schema.create,
      files += (0, 13, 37, timestamp(), "knockknock", "txt", "who's there"),
      files += (1, 14, 37, timestamp(), "test_file", "txt", "test file who?")
    ))

    // Let's wait till we have an actual table, shall we?
    waitMaybe(justAMoment)

    // Insert some more dummy data
    val justBMoment = db.run(files ++= Seq(
        (2, 13, 37, timestamp(), "test02", "txt", "dummy text content"),
        (3, 14, 37, timestamp(), "test03", "txt", "dummy text content"),
        (4, 15, 37, timestamp(), "test04", "txt", "dummy text content")
    ))

    val justCMoment = db.run(files ++= Seq(
        (5, 17, 37, timestamp(), "test05", "txt", "dummy text content"),
        (6, 14, 37, timestamp(), "test06", "txt", "dummy text content"),
        (7, 13, 37, timestamp(), "test07", "txt", "dummy text content")
    ))

    // Make some more interesting changes
    val justDMoment = db.run(files.filter(_.userid === 14).delete)
    val justEMoment = db.run(files.sortBy(_.timestamp).filter(_.userid === 13).result)

  /*
    Okay, look, this shouldn't be hard.
    When I run db.run() on each of the database operations, I get a Future.
    After a db.run() is completed, I want to print out the database contents.

    Unfortunately, the code is likely to close before it finishes printing.
    All my versions of the code that do not do this involve EXCESSIVE blocking.
  */

    // Print contents after each change
    justAMoment onSuccess { case _ =>
      db.run(files.result).map(printEach(_, "Q1:"))
    }

    justBMoment onSuccess { case _ =>
      db.run(files.result).map(printEach(_, "Q2:"))
    }

    justCMoment onSuccess { case _ =>
      db.run(files.result).map(printEach(_, "Q3:"))
    }

    justDMoment onSuccess { case _ =>
      db.run(files.result).map(printEach(_, "Q4:"))
    }

    justEMoment onSuccess { case _ =>
      db.run(files.result).map(printEach(_, "Q5:"))
    }

    // How does *Scala* want me to do this?
    // I just want my print statements to finish.
  } finally db.close
  println("Connection closed.")
}