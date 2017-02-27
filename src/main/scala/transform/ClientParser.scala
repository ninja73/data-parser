package transform

import java.text.SimpleDateFormat
import java.time.{LocalDateTime, ZoneOffset}
import java.util.Calendar

import scala.util.parsing.combinator.{PackratParsers, RegexParsers}

class ClientParser extends RegexParsers with PackratParsers{

  val field = """[^,]*""".r <~ opt(",")
  val integerValue = """[\d]*""".r <~ ","
  val doubleValue = """([\d]+).([\d]+)""".r

  val currentDateTime = LocalDateTime.now()
    .toInstant(ZoneOffset.ofTotalSeconds(0))
    .getEpochSecond

  val resultParser: Parser[ClientInfo] =
    field ~ integerValue ~ integerValue ~ integerValue ~ doubleValue ^^ {
      case userId ~ region ~ firstPurchase ~ ordersCount ~ paymentSum ⇒
        val experience = getExperience(firstPurchase.toLong)
        ClientInfo(
          userId = userId.toLowerCase,
          ordersCount = ordersCount.toInt,
          paymentSum = paymentSum.toDouble,
          region = region,
          experience = experience)
    }

  val otherResultParse: Parser[ClientInfo] =
    """\w{2}""".r ~ "," ~ field ~ field ~ field ~ integerValue ~ doubleValue ^^ {
      case branch ~ _ ~ userId ~ region ~ regDateTime ~ ordersCount ~ paymentSum ⇒
        val format = new SimpleDateFormat("yyy-MM-dd")
        val experience = getExperience(format.parse(regDateTime).getTime / 1000)
        ClientInfo(
          userId = (branch + userId).toLowerCase,
          ordersCount = ordersCount.toInt,
          paymentSum = paymentSum.toDouble,
          region = region,
          experience = experience)
  }

  def getExperience(ts: Long): Int = {
    val date = Calendar.getInstance()
    date.setTimeInMillis((currentDateTime - ts) * 1000)
    date.get(Calendar.DAY_OF_YEAR) - 1
  }

  def root: Parser[ClientInfo] = otherResultParse | resultParser
}

case class ClientInfo(
  userId: String,
  experience: Int,
  ordersCount: Int,
  paymentSum: Double,
  region: String)