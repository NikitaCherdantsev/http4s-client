import SClient.getJson
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import org.http4s.client.blaze.BlazeClientBuilder
import scala.concurrent.ExecutionContext.global

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    BlazeClientBuilder[IO](global).resource
      .use(getJson)
      .as(ExitCode.Success)
}
