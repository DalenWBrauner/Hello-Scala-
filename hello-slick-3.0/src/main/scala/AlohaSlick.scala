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
        println(label ++ s" $id | $userid | $channelid | $timestamp | $filename.$extension : $content")
    }
  }

  // Instantiate Database Connection
  val db = Database.forConfig("h2mem1")
  println("Connected to Database!")

  try {
    // Connect with a table
    val files: TableQuery[Files] = TableQuery[Files]

    // Kickstart with dummy data
    val setupSchema = db.run(DBIO.seq(
      files.schema.create,
      files += (0, 13, 37, timestamp(), "knockknock", "txt", "who's there"),
      files += (1, 14, 37, timestamp(), "test_file", "txt", "test file who?")
    ))

    // Let's wait till we have an actual table, shall we?
    waitMaybe(setupSchema)

    val justAMoment = for {
      // Insert dummy data
      justBMoment <- db.run(files ++= Seq(
        (2, 13, 37, timestamp(), "Favorite Color", "txt", "Green"),
        (3, 14, 37, timestamp(), "Middle Name", "txt", "William"),
        (4, 15, 37, timestamp(), "Favorite Sauce", "txt", "Szechuan")
      ))

      // Insert more dummy data
      justCMoment <- db.run(files ++= Seq(
        (5, 17, 37, timestamp(), "hey", "txt", "don't touch me"),
        (6, 14, 37, timestamp(), "hey you", "txt", "don't touch me"),
        (7, 13, 37, timestamp(), "i said hey you", "txt", "don't touch me")
      ))

      // Delete everything by user 14
      justDMoment <- db.run(files.filter(_.userid === 14).delete)

    } yield (justBMoment, justCMoment, justDMoment)

    // When everything is said and done, print out database contents.
    // There should be a MUCH better way to do this; for comprehension was not the answer.
    waitMaybe(justAMoment)
    waitMaybe(db.run(files.result).map(printEach(_, "Everything:")))

    // Everything submitted by User 13 sorted by filename
    waitMaybe(db.run(
      files.sortBy(_.filename).filter(_.userid === 13).result
    ).map(printEach(_,"DB Query:")))

  } finally db.close
  println("Connection closed.")
}