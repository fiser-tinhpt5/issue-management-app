package user.model

import models.user.User
import play.api.test.PlaySpecification

/**
 * Created by septechuser on 25/10/2016.
 */
class UserSpec extends PlaySpecification {
  "User " should {
    val email = "tinh_pt@septeni-technology.jp"
    val password = "1234"

    "fromFormValue(email: String, password: String) " should {
      "create instance with form value " in {
        val result = User.fromFormValue(email, password)

        result.id must beEqualTo(0)
        result.name must beEqualTo("")
        result.email must beEqualTo(email)
        result.password must beEqualTo(password)
      }
    }

    "toFormValue(user: User) " should {
      "extract value from instance" in {
        val user = User(1, "", email, password)
        val result = User.toFormValue(user)

        result must beSome(email, password)
      }
    }
  }
}
