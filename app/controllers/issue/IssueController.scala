package controllers.issue

import javax.inject.{ Inject, Singleton }

import controllers.Secured
import controllers.forms.IssueForm
import models.issue.{ Issue, IssueDAO }
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{ Action, Controller }

import scala.util.{ Failure, Success }
/**
 * Created by septechuser on 11/10/2016.
 */
@Singleton
class IssueController @Inject() (issueDAO: IssueDAO) extends Controller with Secured {

  val issueForm: Form[IssueForm] = Form(
    mapping(
      "issue" -> nonEmptyText(maxLength = 200),
      "challenge" -> text(maxLength = 200),
      "raised-date" -> date,
      "status" -> text
    )(IssueForm.apply)(IssueForm.unapply)
  )

  def list = Action { implicit request =>
    issueDAO.list match {
      case Success(issues) => Ok(views.html.issue.issue(issues, ""))
      case Failure(e)      => InternalServerError(views.html.error(e))
    }
  }

  def createIssueForm = IsAuthenticated { _ => implicit request =>
    Ok(views.html.issue.create.apply)
  }

  def createIssue = IsAuthenticated { _ => implicit request =>
    issueForm.bindFromRequest.fold(
      hasErrors => BadRequest("Form error"),
      issueForm => {
        val issue = Issue(0, issueForm.issue, issueForm.challenge, issueForm.raisedDate,
          models.issue.Status.fromString(issueForm.status).get)
        issueDAO.save(issue).map(id => Redirect("/issues")).get
      }
    )
  }
}
