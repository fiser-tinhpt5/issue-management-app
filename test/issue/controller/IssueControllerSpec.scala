package issue.controller

import java.text.SimpleDateFormat
import java.util.Date

import controllers.issue.IssueController
import models.issue.Status.NOT_YET
import models.issue.{ Issue, IssueDAO }
import org.specs2.mock.Mockito
import play.api.test.{ FakeRequest, PlaySpecification, WithApplication }
import scalikejdbc.DBSession

import scala.util.{ Failure, Success, Try }

/**
 * Created by septechuser on 13/10/2016.
 */
class IssueControllerSpec extends PlaySpecification with Mockito {
  val mockIssueDAO = mock[IssueDAO]

  def issueControllerWithMock(mockIssueDAO: IssueDAO) = new IssueController(mockIssueDAO)

  val dummyIssue = Issue(1, "write test code for controllers", "know nothing about mock", new Date(), NOT_YET)
  val dummyEmail = "tinh_pt@septeni-technology.jp"
  val format = new SimpleDateFormat("yyyy-MM-dd")

  "IssueController " should {
    "list " should {

      "return all issue list " in new WithApplication() {
        mockIssueDAO.list returns Try(List(dummyIssue))
        val apiResult = call(
          issueControllerWithMock(mockIssueDAO).list,
          FakeRequest(GET, "/issues")
        )
        status(apiResult) mustEqual OK
        contentAsString(apiResult) must contain("write test code for controllers")
      }

      "don't return issue list if there is no issue" in new WithApplication() {
        mockIssueDAO.list returns Try(List())
        val apiResult = call(
          issueControllerWithMock(mockIssueDAO).list,
          FakeRequest(GET, "/issues")
        )
        status(apiResult) mustEqual OK
        contentAsString(apiResult) must contain("No issue")
      }

      "return error with 500 when get issue list fail" in new WithApplication() {
        mockIssueDAO.list returns Failure(new Throwable)
        val apiResult = call(
          issueControllerWithMock(mockIssueDAO).list,
          FakeRequest(GET, "/issues")
        )
        status(apiResult) mustEqual 500
      }
    }

    "createIssueForm" should {
      "redirect to login page if dont have session" in new WithApplication {
        var apiResult = call(
          issueControllerWithMock(mockIssueDAO).createIssueForm,
          FakeRequest(GET, "/issues/create")
        )
        status(apiResult) mustEqual 303
      }

      "show create issue form if have session" in new WithApplication {
        var apiResult = call(
          issueControllerWithMock(mockIssueDAO).createIssueForm,
          FakeRequest(GET, "/issues/create").withSession("email" -> dummyEmail)
        )
        status(apiResult) mustEqual 200
        contentAsString(apiResult).toLowerCase must contain("create new issue")
      }
    }

    "createIssue" should {
      "redirect to login page if dont have session " in new WithApplication() {
        mockIssueDAO.save(dummyIssue) returns Success(1)
        val apiResult = call(
          issueControllerWithMock(mockIssueDAO).createIssue,
          FakeRequest(POST, "/issues")
            .withFormUrlEncodedBody(
              "issue" -> dummyIssue.issue,
              "challenge" -> dummyIssue.challenge,
              "raised-date" -> format.format(dummyIssue.raisedDate),
              "status" -> dummyIssue.status.status
            )
        )
        status(apiResult) mustEqual 303
      }

      "success and redirect to issue list if session and everything is ok " in new WithApplication() {
        mockIssueDAO.save(any[Issue])(any[DBSession]) returns Success(1)
        val apiResult = call(
          issueControllerWithMock(mockIssueDAO).createIssue,
          FakeRequest(POST, "/issues")
            .withSession(("email", dummyEmail))
            .withFormUrlEncodedBody(
              "issue" -> dummyIssue.issue,
              "challenge" -> dummyIssue.challenge,
              "raised-date" -> format.format(dummyIssue.raisedDate),
              "status" -> dummyIssue.status.status
            )
        )
        status(apiResult) mustEqual 303
      }
    }
  }
}
