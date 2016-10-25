package models.user

/**
 * Created by septechuser on 24/10/2016.
 */
case class User(id: Int, name: String, email: String, password: String)

object User {
  def fromFormValue(email: String, password: String): User =
    User(0, "", email, password)

  def toFormValue(user: User): Option[(String, String)] = {
    Some(user.email, user.password)
  }
}