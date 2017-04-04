/**
  * Created by dalenwbrauner on 4/3/17.
  */

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import slick.backend.DatabasePublisher
import slick.driver.H2Driver.api._

object AlohaSlick extends App {

  def timestamp(): String = {
    val logDate = java.time.LocalDate.now
    val logTime = java.time.LocalTime.now
    s"$logDate @ $logTime"
  }

  // Instantiate Database
  print("Instantiating database...")
  val db = Database.forConfig("h2mem1")
  println("done!")

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
    val setupFuture = db.run(setupTest)

    // Print out the current data
    val output1 = files.result.map(_.foreach {
      case (id, userid, channelid, timestamp, filename, extension, content) =>
        println(s"Q1: $id | $userid | $channelid | $timestamp | $filename.$extension : $content")
    })

    val inABit = for {
      insertSome <- db.run(files ++= Seq(
        (2, 13, 37, timestamp(), "test02", "txt", "dummy text content"),
        (3, 14, 37, timestamp(), "test03", "txt", "dummy text content"),
        (4, 15, 37, timestamp(), "test04", "txt", "dummy text content")
      ))

      _ = print("So apparently, print statements are allowed in for-comprehension, ")
      _ = println("but ONLY if they're not on the first line.")

      insertSomeMore <- db.run(files ++= Seq(
        (5, 17, 37, timestamp(), "test05", "txt", "dummy text content"),
        (6, 14, 37, timestamp(), "test06", "txt", "dummy text content"),
        (7, 13, 37, timestamp(), "test07", "txt", "dummy text content")
      ))

      // Print out the data again
    } yield (insertSome, insertSomeMore)

    Await.result(inABit, Duration.Inf)
    db.run(files.result.map(_.foreach {
      case (id, userid, channelid, timestamp, filename, extension, content) =>
        println(s"Q2: $id | $userid | $channelid | $timestamp | $filename.$extension : $content")
    }))

    // Let's modify some of the existing data
//    println("o")

    // Let's make some more complex queries
//    println("p")

    print("Closing database...")
  } finally db.close
  println("done!")
}