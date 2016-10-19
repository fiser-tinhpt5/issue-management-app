package models.issue

/**
 * Created by septechuser on 14/10/2016.
 */
sealed abstract class Status(val status: String)

object Status {
  case object NOT_YET extends Status("NOT YET")
  case object DOING extends Status("DOING")
  case object DONE extends Status("DONE")
}
