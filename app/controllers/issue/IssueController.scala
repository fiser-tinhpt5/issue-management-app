package controllers.issue

import javax.inject.{ Inject, Singleton }

import models.issue.{ Issue, IssueDAO }
import play.api.mvc.{ Action, Controller }
/**
 * Created by septechuser on 11/10/2016.
 */
@Singleton
class IssueController @Inject() (issueDAO: IssueDAO) extends Controller {

  def list = Action {
    val issues: List[Issue] = issueDAO.list
    Ok(views.html.main.render("List of user", views.html.issue.issue.render(issues)))
  }

}
