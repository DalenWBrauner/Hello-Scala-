/**
  * Created by dalenwbrauner on 4/3/17.
  */

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import slick.backend.DatabasePublisher
import slick.driver.H2Driver.api._

object AlohaSlick extends App {
  println("a")

  // Instantiate Database
  val db = Database.forConfig("h2mem1")
  println("b")

  try {
    println("c")

    // Connect with a table
    val files: TableQuery[Files] = TableQuery[Files]
    println("d")

    // Insert some dummy data
    val setupTest: DBIO[Unit] = DBIO.seq(
      files.schema.create,
      files += (0,13,37,"today","knockknock","txt","who's there"),
      files += (1,14,37,"today","test_file","txt","test file who?")
    )
    println("e")

    // Execute
    val setupFuture = db.run(setupTest)
    println("f")

    // This is what I *WANT* to do
    val f = setupFuture.map { _ =>
      println("g")

      val output1 = db.run(files.result).map(_.foreach {
        case (id, userid, channelid, timestamp, filename, extension, content) =>
          println(s"Output_1: $id | $userid | $channelid | $timestamp | $filename.$extension : $content")
      })
      println("h")

    }.map { _ =>

      val insertSome: DBIO[Option[Int]] = files ++= Seq(
        (2, 15, 37, "today", "test02", "txt", "dummy text content"),
        (3, 15, 37, "today", "test03", "txt", "dummy text content"),
        (4, 15, 37, "today", "test04", "txt", "dummy text content")
      )
      println("i")

      val insertSomeMore: DBIO[Option[Int]] = files ++= Seq(
        (5, 16, 37, "today", "test05", "txt", "dummy text content"),
        (6, 16, 37, "today", "test06", "txt", "dummy text content"),
        (7, 16, 37, "today", "test07", "txt", "dummy text content")
      )
      println("j")

      db.run(insertSome)
      println("k")

      db.run(insertSomeMore)
      println("l")

    }.map { _ =>
      println("m")

      val output2 = db.run(files.result.map(_.foreach {
        case (id, userid, channelid, timestamp, filename, extension, content) =>
          println(s"Output_2: $id | $userid | $channelid | $timestamp | $filename.$extension : $content")
      }))
      println("n")

    }
    println("o")

    Await.result(f, Duration.Inf)
    println("p")

  } finally db.close
  println("z")
}