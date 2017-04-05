/**
  * Created by dalenwbrauner on 4/3/17.
  */

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import slick.driver.H2Driver.api._

object AlohaSlick extends App {

  def timestamp(): String = {
    val logDate = java.time.LocalDate.now
    val logTime = java.time.LocalTime.now
    s"$logDate @ $logTime"
  }

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

    // Insert some dummy data
    val setupTest: DBIO[Unit] = DBIO.seq(
      files.schema.create,
      files += (0, 13, 37, timestamp(), "knockknock", "txt", "who's there"),
      files += (1, 14, 37, timestamp(), "test_file", "txt", "test file who?")
    )

    // Execute
    db.run(setupTest)

    // Print out the current data
    db.run(files.result).map(printEach(_,"Q1:"))

    // Insert some more dummy data
    val inABit = for {
      insertSome <- db.run(files ++= Seq(
        (2, 13, 37, timestamp(), "test02", "txt", "dummy text content"),
        (3, 14, 37, timestamp(), "test03", "txt", "dummy text content"),
        (4, 15, 37, timestamp(), "test04", "txt", "dummy text content")
      ))

      insertSomeMore <- db.run(files ++= Seq(
        (5, 17, 37, timestamp(), "test05", "txt", "dummy text content"),
        (6, 14, 37, timestamp(), "test06", "txt", "dummy text content"),
        (7, 13, 37, timestamp(), "test07", "txt", "dummy text content")
      ))
    } yield (insertSome, insertSomeMore)

    // Spit it out again
    Await.result(inABit, Duration.Inf)
    db.run(files.result).map(printEach(_,"Q2:"))

    // Make some more interesting changes
    val inBBit = for {
      noMoreFourteen <- db.run(files.filter(_.userid === 14).delete)
      gimmieThirteen <- db.run(files.sortBy(_.timestamp).filter(_.userid === 13).result)
    } yield (noMoreFourteen, gimmieThirteen)

    // Wait and spit it out again
    Await.result(inBBit, Duration.Inf)
    db.run(files.result).map(printEach(_,"Q3:"))

  } finally db.close
  println("Connection closed.")
}