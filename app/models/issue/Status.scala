package models.issue

import scala.util.Try

/**
 * Created by septechuser on 14/10/2016.
 */
sealed abstract class Status(val status: String)

object Status {
  case object NOT_YET extends Status("NOT YET")
  case object DOING extends Status("DOING")
  case object DONE extends Status("DONE")

  def fromString(str: String): Try[Status] = Try {
    str match {
      case "NOT YET" => NOT_YET
      case "DOING"   => DOING
      case "DONE"    => DONE
      case _         => throw new IllegalArgumentException(s"${str} is not defined as Status")
    }
  }
}
