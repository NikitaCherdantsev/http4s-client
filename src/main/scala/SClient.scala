import cats.effect.{Blocker, ContextShift, IO, Timer}
import fs2.Stream
import fs2.io.stdout
import fs2.text.{lines, utf8Encode}
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Json}
import jawnfs2._
import org.http4s.circe._
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.{EntityDecoder, Method, Request}
import org.typelevel.jawn.Facade
import io.circe.generic.auto._
import io.circe.fs2._

import scala.concurrent.ExecutionContext.global

case class JsonInfo(time: String, message: String)

class HardStream {
  implicit val f: Facade[Json]      = new io.circe.jawn.CirceSupportParser(None, false).facade
  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO]     = IO.timer(global)
//    implicit val jsonsDecoder: Decoder[JsonInfo] = deriveDecoder[JsonInfo]
//    implicit val jsonsDecoder: Decoder[JsonInfo] = Decoder.instance{
//      h =>
//      for {
//          time <- h.get[String]("time")
//          msg <- h.get[String]("message")
//      } yield JsonInfo(time, msg)
//    }

  def run: IO[Unit] =
    Stream
      .resource(Blocker[IO])
      .flatMap { blocker =>
        stream(blocker)
      }
      .compile
      .drain

  def stream(blocker: Blocker): Stream[IO, Unit] = {
    val req = Request[IO](Method.GET, uri"http://127.0.0.1:8080/json/someMessage")
    val resp = for {
      client <- BlazeClientBuilder[IO](global).stream
      res <- client
              .stream(req)
              .flatMap(_.body.chunks.parseJsonStream)
              .through(decoder[IO, JsonInfo])
              .map(i => i.time + ":" + i.message)
              .through(lines)
              .through(utf8Encode)
              .through(stdout(blocker))
    } yield res
    resp
//    resp
//      .through(decoder[IO, JsonInfo])
//      .map(i => i.time + ":" + i.message)
//      .through(lines)
//      .through(utf8Encode)
//      .through(stdout(blocker))
  }
}

object SClient {

  implicit val jsonsDecoder: Decoder[JsonInfo]          = deriveDecoder[JsonInfo]
  implicit val cs: ContextShift[IO]                     = IO.contextShift(global)
  implicit val timer: Timer[IO]                         = IO.timer(global)
  implicit val f: Facade[Json]                          = new io.circe.jawn.CirceSupportParser(None, false).facade
  implicit val userDecoder: EntityDecoder[IO, JsonInfo] = jsonOf[IO, JsonInfo]

  def getJson(client: Client[IO]): IO[Unit] = IO {

    val req = Request[IO](Method.GET, uri"http://127.0.0.1:8080/json/someMessage")
    val res = client.expect[JsonInfo](req)

    res.map(println)
  }

}
