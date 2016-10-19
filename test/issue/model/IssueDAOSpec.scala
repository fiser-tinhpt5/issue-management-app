package issue.model

import models.issue.IssueDAO
import play.api.test.{ FakeApplication, PlaySpecification }
import scalikejdbc.DBSession
import scalikejdbc.specs2.mutable.AutoRollback
import scalikejdbc._
import scalikejdbc.config.DBs
/**
 * Created by septechuser on 13/10/2016.
 */
class IssueDAOSpec extends PlaySpecification {

  val issueDAO = new IssueDAO

  "IssueDAO.list" should {
    "return all issue list " in new AutoRollbackWithFuture {
      /*running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {*/
      sql"""INSERT INTO issue(issue, raised_date, challenge, status)
      VALUES ('Write some code handing error in controller', '2016-10-18', 'Dont understand how to handle error', 'DOING')"""
        .update().apply()
      val issues = issueDAO.list.get
      issues.length should be > 0
      //}
    }

    "return empty Seq if there is no issue" in new AutoRollbackWithFuture {
      /*running(FakeApplication()) {*/
      val issues = issueDAO.list.get
      issues should have length 0

    }
  }

  trait AutoRollbackWithFuture extends AutoRollback {
    override def fixture(implicit session: DBSession): Unit = {

    }
  }
}

