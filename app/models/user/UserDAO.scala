package models.user

import scalikejdbc.{ AutoSession, DBSession }
import scalikejdbc._
import scala.util.Try

/**
 * Created by septechuser on 24/10/2016.
 */
class UserDAO {
  def authenticate(email: String, password: String)(implicit session: DBSession = AutoSession): Try[Option[User]] =
    Try {
      sql"select * from user where email = ${email} and password = ${password}"
        .map(rs => User(rs.int("id"), rs.string("name"), rs.string("email"), rs.string("password")))
        .single().apply()
    }
}
