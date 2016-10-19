package issue.controller

import java.util.Date

import controllers.issue.IssueController
import models.issue.{ Issue, IssueDAO }
import org.specs2.mock.Mockito
import play.api.test.{ FakeRequest, PlaySpecification, WithApplication }

import scala.util.{ Failure, Try }

/**
 * Created by septechuser on 13/10/2016.
 */
class IssueControllerSpec extends PlaySpecification with Mockito {
  val mockIssueDAO = mock[IssueDAO]

  def issueControllerWithMock(mockIssueDAO: IssueDAO) = new IssueController(mockIssueDAO)

  val dummyIssue = Issue(1, "write test code for controllers", "know nothing about mock", new Date(), "NOT YET")

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
  }
}
