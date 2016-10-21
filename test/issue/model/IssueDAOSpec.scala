package issue.model

import models.issue.IssueDAO
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import play.api.test.PlaySpecification
import scalikejdbc.specs2.mutable.AutoRollback
import scalikejdbc._
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
      VALUES ('Write some code handing error in controller', '2016-10-18', 'Dont understand how to handle error', 'DOING')"""
        .update().apply()
      val issues = issueDAO.list.get
      issues must haveSize(1)
      issues.head.status.status must beEqualTo("DOING")
      issues.head.issue must beEqualTo("Write some code handing error in controller")
      issues.head.challenge must beEqualTo("Dont understand how to handle error")
    }

    "return empty Seq if there is no issue" in new AutoRollback {
      val issues = issueDAO.list.get
      issues must beEmpty
    }
  }

}

