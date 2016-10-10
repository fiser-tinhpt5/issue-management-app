package controllers

import javax.inject.{ Inject, Singleton }

import models.User
import play.api.libs.json.Json
import play.api.mvc.{ Action, Call, Controller, Result }
import scalikejdbc._
import scalikejdbc.config.DBs
import play.api.data._
import play.api.data.Forms._

/**
 * Created by septechuser on 07/10/2016.
 */
@Singleton
class UserController @Inject extends Controller {

  DBs.setupAll()
  val userForm: Form[User] = Form(
    mapping(
      "id" -> number,
      "user-name" -> text,
      "password" -> text
    )(User.apply)(User.unapply)
  )

  def getAllUser = Action { implicit request =>
    val users: List[User] = DB readOnly { implicit session =>
      sql"select * from User".map(rs => User(rs.int("id"), rs.string("user_name"), rs.string("password"))).list().apply()
    }

    Ok(views.html.templatelayout.render("List of user", views.html.userstable.render(users)))
  }

  def updateUser = Action { implicit request =>
    val user = userForm.bindFromRequest().get
    val count = DB autoCommit { implicit session =>
      sql"update User set user_name = ${user.userName}, password = ${user.password} where id = ${user.id}"
        .update().apply()
    }
    Redirect(routes.UserController.getAllUser())
  }

  def addUser = Action { implicit request =>

    Redirect(routes.UserController.getAllUser())
  }
}
