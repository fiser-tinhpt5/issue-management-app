package controllers.user

import javax.inject.{ Inject, Singleton }

import controllers.routes
import models.user.{ User, UserDAO }
import play.api.data.Forms._
import play.api.data._
import play.api.mvc.{ Action, Controller }

/**
 * Created by septechuser on 07/10/2016.
 */
@Singleton
class UserController @Inject() (userDAO: UserDAO) extends Controller {

  val userForm: Form[User] = Form(
    mapping(
      "id" -> number,
      "userName" -> text,
      "password" -> text
    )(User.apply)(User.unapply)
  )

  def getAllUser = Action {
    val users: List[User] = userDAO.getAll
    Ok(views.html.main.render("List of user", views.html.user.userstable.render(users)))
  }

  def updateUser = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("Error")
      },
      userData => {
        val count = userDAO.update(userData)
        Redirect(routes.UserController.getAllUser())
      }
    )

  }

  def addUser = Action { implicit request =>

    Redirect(routes.UserController.getAllUser())
  }
}
