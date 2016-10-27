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

  def list = Action { implicit request =>
    issueDAO.list match {
      case Success(issues) => Ok(views.html.issue.issue(issues))
      case Failure(e)      => InternalServerError(views.html.error(e))
    }
  }
}
