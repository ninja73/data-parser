package transform

import scala.util.parsing.combinator.RegexParsers

trait BaseTransform[CustomParseResult] extends Serializable {

  trait SimpleParsers extends RegexParsers
    with BaseParsers {
    type RootType = CustomParseResult
  }

  def parser: SimpleParsers

}

