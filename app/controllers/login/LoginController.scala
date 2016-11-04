package controllers.login

import javax.inject.{ Inject, Singleton }

import controllers.Secured
import controllers.forms.LoginForm
import models.user.{ User, UserDAO }
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{ Action, Controller }

import scala.util.{ Failure, Success }

/**
 * Created by septechuser on 24/10/2016.
 */
@Singleton
class LoginController @Inject() (userDAO: UserDAO) extends Controller with Secured {

  val loginForm: Form[LoginForm] = Form(
    mapping(
      "email" -> text.verifying("Invalid email format", { !_.matches("""(?=[^\s]+)(?=(\w+)@([\w\.]+))""".r.toString()) }),
      "password" -> nonEmptyText
    )(LoginForm.apply)(LoginForm.unapply)
  )

  def login = Action { implicit request =>
    Ok(views.html.user.login("")).withNewSession
  }

  def logout = IsAuthenticated { email => implicit request =>
    Redirect("/issues").withNewSession
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      hasError => BadRequest(views.html.user.login("Bad request")),
      login => userDAO.authenticate(login.email, login.password) match {
        case Success(user) => Redirect("/issues").withSession("email" -> user.email)
        case Failure(e)    => BadRequest(views.html.user.login("Invalid email or password"))
      }
    )
  }
}
