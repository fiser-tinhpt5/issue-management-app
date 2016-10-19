package controllers.issue

import javax.inject.{ Inject, Singleton }

import models.issue.{ Issue, IssueDAO }
import play.api.Logger
import play.api.mvc.{ Action, Controller }

import scala.util.{ Failure, Success }
/**
 * Created by septechuser on 11/10/2016.
 */
@Singleton
class IssueController @Inject() (issueDAO: IssueDAO) extends Controller {

  def list = Action {
    issueDAO.list match {
      case Success(issues) => Ok(views.html.main.render("List of user", views.html.issue.issue.render(issues)))
      case Failure(e)      => InternalServerError(views.html.main.render("Error", views.html.error.render(e)))
    }
  }
}
