package models

import scalikejdbc.{ AutoSession, DBSession }
import scalikejdbc.config.DBs

import scala.util.Try

/**
 * Created by septechuser on 11/10/2016.
 */
abstract class AbstractDAO[T] {
  def list(implicit session: DBSession): Try[List[T]]
}
