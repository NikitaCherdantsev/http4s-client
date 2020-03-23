import cats.effect._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import org.http4s.Status.{NotFound, Successful}
import org.http4s.circe._
import org.http4s.client.Client

object SClient {

  implicit val jsonsDecoder: Decoder[JsonInfo] = deriveDecoder[JsonInfo]

  def getJson(client: Client[IO]): IO[Unit] = IO {

    val resp = client.get("http://127.0.0.1:8080/json/someMessage"){
      case Successful(response) =>
        response.decodeJson[JsonInfo].map(a => "Json decoded:  " + a.time + ": " + a.message)
      case NotFound(_) => IO.pure("Not Found")
    }

    println(resp.unsafeRunSync())
  }

}

final case class JsonInfo(time: String, message: String)
