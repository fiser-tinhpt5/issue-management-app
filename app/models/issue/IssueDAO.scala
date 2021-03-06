package models.issue

import javax.inject.Inject

import models.AbstractDAO
import models.issue.Status._
import scalikejdbc._

import scala.util.{ Failure, Success, Try }

/**
 * Created by septechuser on 11/10/2016.
 */
class IssueDAO extends AbstractDAO[Issue] {
  override def list(implicit session: DBSession = AutoSession): Try[List[Issue]] = {
    Try {
      sql"select * from issue".
        map(rs =>
          Issue(rs.int("id"), rs.string("issue"), rs.string("challenge"), rs.date("raised_date"),
            Status.fromString(rs.string("status")).map(status => status).get))
        .list().apply()
    }
  }

  override def save(t: Issue)(implicit session: DBSession = AutoSession): Try[Long] = {
    Try {
      sql"""insert into issue(issue, challenge, raised_date, status)
           values(${t.issue}, ${t.challenge}, ${t.raisedDate}, ${t.status.status})"""
        .updateAndReturnGeneratedKey().apply()
    }
  }
}
