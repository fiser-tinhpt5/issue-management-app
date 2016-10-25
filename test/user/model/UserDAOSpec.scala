package user.model

import models.user.{ User, UserDAO }
import play.api.test.PlaySpecification
import setting.DBTestSetting
import scalikejdbc._
import scalikejdbc.specs2.AutoRollback

import scala.util.Success

/**
 * Created by septechuser on 25/10/2016.
 */
class UserDAOSpec extends PlaySpecification with DBTestSetting {

  val userDAO = new UserDAO
  val name = "Tinh"
  val email = "tinh_pt@septeni-technology"
  val password = "1234"
  trait AutoRollbackFeature extends AutoRollback {
    sql"""
       insert into user(`name`,`email`,`password`) values(${name},${email},${password})
      """.update().apply()
  }

  "UserDAO " should {
    "authenticate(email: String, password: String) " should {
      "return user if email and password are correct" in new AutoRollbackFeature {
        userDAO.authenticate(email, password) match {
          case Success(Some(user)) => {
            user.name must beEqualTo(name)
            user.email must beEqualTo(email)
            user.password must beEqualTo(password)
          }
        }
      }

      "return none if password is same but email is different" in new AutoRollbackFeature {
        userDAO.authenticate("abcde@123.com", password) must beEqualTo(Success(None))
      }

      "return none if email is same but password is different" in new AutoRollbackFeature {
        userDAO.authenticate(email, "123") must beEqualTo(Success(None))
      }

      "return none if email and password are different" in new AutoRollbackFeature {
        userDAO.authenticate("abcde@123.com", "123") must beEqualTo(Success(None))
      }
    }
  }
}
