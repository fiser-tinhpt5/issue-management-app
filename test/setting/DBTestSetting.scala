package setting

import scalikejdbc.ConnectionPool

/**
 * Created by septechuser on 21/10/2016.
 */
trait DBTestSetting {
  def loadJDBC(): Unit = {
    val url = "jdbc:mysql://localhost:3306/issue_management_test"
    val username = "root"
    val password = "1201"
    ConnectionPool.singleton(url, username, password)
  }

  loadJDBC()
}
