package user.model

import models.user.Password
import play.api.test.PlaySpecification

/**
 * Created by septechuser on 31/10/2016.
 */
class PasswordSpec extends PlaySpecification {
  val password = "1234"
  val encryptPassword = "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4"

  "Password " should {
    "encrypt with SHA-256 return password after encrypting" in {
      Password.encrypt(password) must beEqualTo(encryptPassword)
    }
  }
}
