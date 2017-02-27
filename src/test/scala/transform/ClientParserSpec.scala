package transform

import java.text.SimpleDateFormat
import java.time.{LocalDateTime, ZoneOffset}

import org.specs2.matcher
import org.specs2.mutable

class ClientParserSpec extends mutable.Specification
  with matcher.ParserMatchers  {
  import MsgParsers._

  val currentTs = LocalDateTime.now()
    .toInstant(ZoneOffset.ofTotalSeconds(0))
    .getEpochSecond

  val ts = LocalDateTime.now().minusDays(200)
    .toInstant(ZoneOffset.ofTotalSeconds(0))
    .getEpochSecond


  val msg1 = s"thomas98@gmail.com, 2, $ts, 3, 142.8"
  val msg2 = s"NW, 37, Карелия, ${new SimpleDateFormat("y-M-d").format(ts * 1000L)}, 14, 213.78"

  val result1 = ClientInfo(
    userId = "thomas98@gmail.com",
    experience = 200,
    ordersCount = 3,
    paymentSum = 142.8,
    region = "2")

  val result2 = ClientInfo(
    userId = "NW37",
    experience = 200,
    ordersCount = 14,
    paymentSum = 213.78,
    region = "Карелия")

  "ClientParser parser" should {
    "Parse value part of message" in {
      resultParser must succeedOn(msg1).withResult(result1)
      otherResultParse must succeedOn(msg2).withResult(result2)
    }
    "Calculate user experience" in {
      val ts = LocalDateTime.now().minusDays(42)
        .toInstant(ZoneOffset.ofTotalSeconds(0))
        .getEpochSecond
      getExperience(ts) mustEqual 42
    }
  }

  val parsers = MsgParsers
}

object MsgParsers extends ClientParser