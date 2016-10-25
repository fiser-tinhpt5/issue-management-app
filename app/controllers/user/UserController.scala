package controllers.user

import javax.inject.{ Inject, Singleton }

import controllers.Secured
import models.issue.Issue
import models.user.{ User, UserDAO }
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{ Action, Controller }

import scala.util.{ Failure, Success }

/**
 * Created by septechuser on 24/10/2016.
 */
@Singleton
class UserController @Inject() (userDAO: UserDAO) extends Controller with Secured {

  val loginForm: Form[User] = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(User.fromFormValue)(User.toFormValue)
  )

  def login = Action { implicit request =>
    Ok(views.html.user.login.apply).withNewSession
  }

  def logout = IsAuthenticated { email => implicit request =>
    Redirect("/issues").withNewSession
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      hasError => BadRequest("Bad request"),
      login => userDAO.authenticate(login.email, login.password) match {
        case Success(optionUser) => optionUser match {
          case Some(user) => Redirect("/issues").withSession("email" -> user.email)
          case None       => BadRequest("Invalid email or password")
        }
        case Failure(e) => InternalServerError(views.html.error(e))
      }
    )
  }
}
