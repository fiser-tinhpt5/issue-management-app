package setting

import scalikejdbc.ConnectionPool
import com.typesafe.config.ConfigFactory
/**
 * Created by septechuser on 21/10/2016.
 */
trait DBTestSetting {

  val config = ConfigFactory.load()
  def loadJDBC(): Unit = {
    val url = config.getString("db.test.url")
    val username = config.getString("db.test.username")
    val password = config.getString("db.test.password")
    ConnectionPool.singleton(url, username, password)
  }

  loadJDBC()
}
