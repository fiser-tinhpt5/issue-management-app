package issue.controller

import java.util.Date

import controllers.issue.IssueController
import models.issue.{ Issue, IssueDAO }
import org.specs2.mock.Mockito
import play.api.test.{ FakeRequest, PlaySpecification, WithApplication }

import scala.util.Try

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
        mockIssueDAO.list returns List(dummyIssue)
        val apiResult = call(
          issueControllerWithMock(mockIssueDAO).list,
          FakeRequest(GET, "/issues")
        )
        status(apiResult) mustEqual OK
        contentAsString(apiResult) must contain("write test code for controllers")
      }

      "don't return issue list if there is no issue" in new WithApplication() {
        mockIssueDAO.list returns List()
        val apiResult = call(
          issueControllerWithMock(mockIssueDAO).list,
          FakeRequest(GET, "/issues")
        )
        status(apiResult) mustEqual OK
        contentAsString(apiResult) must contain("No issue")
      }
    }
  }
}
