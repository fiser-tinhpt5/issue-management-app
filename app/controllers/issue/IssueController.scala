package controllers.issue

import javax.inject.{ Inject, Singleton }

import controllers.Secured
import models.issue.{ Issue, IssueDAO }
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{ Action, Controller }

import scala.util.{ Failure, Success }
/**
 * Created by septechuser on 11/10/2016.
 */
@Singleton
class IssueController @Inject() (issueDAO: IssueDAO) extends Controller with Secured {

  val issueCreateForm: Form[Issue] = Form(
    mapping(
      "issue" -> nonEmptyText,
      "challenge" -> nonEmptyText
    )(Issue.fromFormValue)(Issue.toFormValue)
  )

  def createIssue = IsAuthenticated { email => implicit request =>
    issueCreateForm.bindFromRequest().fold(
      hasErrors => BadRequest("Bad request"),
      issue => {
        issueDAO.save(issue) match {
          case Success(issueID) => Redirect("/issues")
          case Failure(e)       => InternalServerError(views.html.error(e))
        }
      }
    )
  }
  def list = Action { implicit request =>
    issueDAO.list match {
      case Success(issues) => Ok(views.html.issue.issue(issues))
      case Failure(e)      => InternalServerError(views.html.error(e))
    }
  }
}
