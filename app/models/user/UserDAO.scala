package models.user

import javax.inject.Inject

import models.AbstractDAO
import scalikejdbc.{ DB, _ }

/**
 * Created by septechuser on 11/10/2016.
 */
class UserDAO @Inject extends AbstractDAO[User] {

  override def getAll: List[User] = {
    val users: List[User] = DB readOnly { implicit session =>
      sql"select * from User"
        .map(rs => User(rs.int("id"), rs.string("user_name"), rs.string("password"))).list().apply()
    }
    users
  }

  override def getById(id: Int): Option[User] = {
    DB readOnly { implicit session =>
      sql"select * from User where id=${id}"
        .map(rs =>
          User(rs.int("id"), rs.string("user_name"), rs.string("password"))).single.apply()
    }
  }

  override def save(t: User): Int = {
    val count = DB autoCommit { implicit session =>
      sql"insert User(user_name, password) VALUES (${t.userName},${t.password})"
        .update().apply()
    }
    count
  }

  override def update(t: User): Int = {
    val count = DB autoCommit { implicit session =>
      sql"update User set user_name = ${t.userName}, password = ${t.password} where id = ${t.id}"
        .update().apply()
    }
    count
  }

  override def delete(t: User): Unit = {

  }
}
