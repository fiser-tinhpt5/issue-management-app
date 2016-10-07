package controllers

import javax.inject.{ Inject, Singleton }

import models.User
import play.api.libs.json.Json
import play.api.mvc.{ Action, Controller }
import scalikejdbc._
import scalikejdbc.config.DBs

/**
 * Created by septechuser on 07/10/2016.
 */
@Singleton
class UserController @Inject extends Controller {

  DBs.setupAll()

  def getUser = Action {
    val users: List[User] = DB readOnly { implicit session =>
      sql"select * from User".map(rs => User(rs.string("user_name"), rs.string("password"))).list().apply()
    }

    Ok(views.html.templatelayout.render("List of user", views.html.userstable.render(users)))
  }

}
