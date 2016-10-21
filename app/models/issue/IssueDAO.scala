package models.issue

import javax.inject.Inject

import models.AbstractDAO
import models.issue.Status._
import scalikejdbc._

import scala.util.Try

/**
 * Created by septechuser on 11/10/2016.
 */
class IssueDAO extends AbstractDAO[Issue] {
  override def list(implicit session: DBSession = AutoSession): Try[List[Issue]] = {
    Try {
      sql"select * from issue".
        map(rs =>
          Issue(rs.int("id"), rs.string("issue"), rs.string("challenge"), rs.date("raised_date"),
            rs.string("status") match {
              case "NOT YET" => NOT_YET
              case "DONE"    => DONE
              case "DOING"   => DOING
            }))
        .list().apply()
    }
  }
}
