package models

import play.api.libs.json.Json

/**
 * Created by septechuser on 07/10/2016.
 */
case class User(userName: String, password: String)

object User {
  implicit val userFormat = Json.format[User]
}
