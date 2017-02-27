package transform

import utils.Logging

import scala.util.parsing.combinator.RegexParsers

trait BaseParsers extends Logging {
  this: RegexParsers ⇒

  type RootType

  def root: Parser[RootType]

  def parseRoot(string: String): Option[RootType] = {
    parseAll(root, string) match {
      case Success(result, _) ⇒ Some(result)
      case NoSuccess(info, rest) ⇒
        log_error(s"Parsed filed: $info")
        None
    }
  }
}

