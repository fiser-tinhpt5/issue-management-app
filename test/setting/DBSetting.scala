package setting

import scalikejdbc.ConnectionPool

/**
 * Created by septechuser on 13/10/2016.
 */
trait DBSetting {
  def loadJDBC = {
    val url = "jdbc:mysql://localhost:3306/issue_management_app"
    val user = "root"
    val password = "1201"
    ConnectionPool.singleton(url, user, password)
  }

  loadJDBC
}
