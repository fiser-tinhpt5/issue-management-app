package issue.model

import java.util.Date

import models.issue.{ Issue, IssueDAO, Status }
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import play.api.test.PlaySpecification
import scalikejdbc.specs2.mutable.AutoRollback
import scalikejdbc._
import setting.DBTestSetting
/**
 * Created by septechuser on 13/10/2016.
 */
@RunWith(classOf[JUnitRunner])
class IssueDAOSpec extends PlaySpecification with DBTestSetting {

  sequential

  val issueDAO = new IssueDAO

  "IssueDAO.list" should {
    "return all issue list " in new AutoRollback {
      sql"""INSERT INTO issue(issue, raised_date, challenge, status)
      VALUES ('Write some code handing error in controller', '2016-10-18', 'Dont understand how to handle error', 'DONE')"""
        .update().apply()
      val issues = issueDAO.list.get
      issues must haveSize(1)
      issues.head.status.status must beEqualTo("DONE")
      issues.head.issue must beEqualTo("Write some code handing error in controller")
      issues.head.challenge must beEqualTo("Dont understand how to handle error")
    }

    "return empty Seq if there is no issue" in new AutoRollback {
      val issues = issueDAO.list.get
      issues must beEmpty
    }
  }

  "IssueDAO.save" should {
    "return genereated key " in new AutoRollback {
      val dummyIssue = Issue(0, "Write some code handing error in controller", "Dont understand how to handle error",
        new Date(), Status.NOT_YET)
      val generatedKey = issueDAO.save(dummyIssue)

      generatedKey.get.toInt should be > 0
    }
  }
}

