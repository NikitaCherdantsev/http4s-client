import SClient._
import cats.effect.{ExitCode, IO}
import cats.implicits._
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.ExecutionContext.global

object Main extends App {

//  def run(args: List[String]): IO[ExitCode] =
//    BlazeClientBuilder[IO](global).resource
//      .use(getJson)
//      .as(ExitCode.Success)

  def run(args: List[String]): IO[ExitCode] =
    (new HardStream).run.as(ExitCode.Success)
}
