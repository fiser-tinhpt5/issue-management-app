package models.issue

import javax.inject.Inject

import models.AbstractDAO
import scalikejdbc.{ DB, _ }

import scala.util.{ Failure, Success, Try }

/**
 * Created by septechuser on 11/10/2016.
 */
class IssueDAO @Inject() extends AbstractDAO[Issue] {
  override def list: List[Issue] = {
    val issues = Try {
      DB readOnly { implicit session =>
        sql"select * from issue".
          map(rs =>
            Issue(rs.int("id"), rs.string("issue"), rs.string("challenge"), rs.date("raised_date"), rs.string("status")))
          .list().apply()
      }
    }
    issues match {
      case Success(list) => list
      case Failure(e) => List()
    }
  }
}
