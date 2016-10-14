package issue.model

import javax.inject.Singleton

import models.issue.IssueDAO
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import play.api.test.{ FakeApplication, PlaySpecification, WithApplication }
import scalikejdbc.config.DBs

/**
 * Created by septechuser on 13/10/2016.
 */
@Singleton
@RunWith(classOf[JUnitRunner])
class IssueDAOSpec extends PlaySpecification {

  val issueDAO = new IssueDAO
  "IssueDAO.list" should {
    "return all issue list " in {
      running(FakeApplication()) {
        val issues = issueDAO.list
        issues.length should be > 0
      }
    }

    "return empty Seq if there is no issue" in {
      running(FakeApplication()) {
        val issues = issueDAO.list
        issues should have length 0
      }
    }
  }
}

