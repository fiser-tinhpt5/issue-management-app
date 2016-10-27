package login.controller

import controllers.login.LoginController
import models.user.{ Password, User, UserDAO }
import org.specs2.mock.Mockito
import play.api.test.{ FakeRequest, PlaySpecification, WithApplication }

import scala.util.{ Failure, Success, Try }

/**
 * Created by septechuser on 25/10/2016.
 */
class LoginControllerSpec extends PlaySpecification with Mockito {

  val mockUserDAO = mock[UserDAO]

  def userControllerWithMock(userDAO: UserDAO) = new LoginController(mockUserDAO)

  val dummyPassword = "1234"
  val dummyUser = User(1, "Tinh", "tinh_pt@septeni-technology", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4")

  "LoginController " should {
    "login " should {
      "redirect to login screen" in new WithApplication() {
        val apiResult = call(
          userControllerWithMock(mockUserDAO).login,
          FakeRequest(GET, "/login")
        )

        status(apiResult) mustEqual 200
        contentAsString(apiResult).toLowerCase must contain("email")
        contentAsString(apiResult).toLowerCase must contain("password")
      }
    }

    "logout " should {
      "redirect to list of issues screen and remove session" in new WithApplication() {
        val apiResult = call(
          userControllerWithMock(mockUserDAO).logout,
          FakeRequest(GET, "/logout")
        )
        status(apiResult) mustEqual 303
        session(apiResult).get("email") must beEqualTo(None)
      }
    }

    "authenticate" should {
      "return email of user as authentication key of session when login success" in new WithApplication() {
        mockUserDAO.authenticate(dummyUser.email, dummyPassword) returns Try(Some(dummyUser))
        val apiResult = call(
          userControllerWithMock(mockUserDAO).authenticate,
          FakeRequest(POST, "/authenticate").withFormUrlEncodedBody("email" -> dummyUser.email, "password" -> dummyPassword)
        )

        status(apiResult) mustEqual 303
        session(apiResult).get("email").get must beEqualTo(dummyUser.email)
      }

      "return bad request if bindFormRequest has error " in new WithApplication() {
        mockUserDAO.authenticate(dummyUser.email, dummyPassword) returns Try(Some(dummyUser))
        val apiResult = call(
          userControllerWithMock(mockUserDAO).authenticate,
          FakeRequest(POST, "/authenticate").withFormUrlEncodedBody("emailUser" -> dummyUser.email, "passwordUser" -> dummyPassword)
        )

        status(apiResult) mustEqual 400
        session(apiResult).get("email") must beEqualTo(None)
      }

      "return bad request if email or password is error" in new WithApplication() {
        val email = "abc@123.123"
        val password = "12"
        mockUserDAO.authenticate(email, password) returns Try(None)
        val apiResult = call(
          userControllerWithMock(mockUserDAO).authenticate,
          FakeRequest(POST, "/authenticate").withFormUrlEncodedBody("email" -> email, "password" -> password)
        )

        status(apiResult) mustEqual 400
        session(apiResult).get("email") must beEqualTo(None)
      }

      "return error 500 when authenticating fail" in new WithApplication() {
        mockUserDAO.authenticate(dummyUser.email, dummyPassword) returns Failure(new Throwable)
        val apiResult = call(
          userControllerWithMock(mockUserDAO).authenticate,
          FakeRequest(POST, "/authenticate").withFormUrlEncodedBody("email" -> dummyUser.email, "password" -> dummyPassword)
        )

        status(apiResult) mustEqual 500
        session(apiResult).get("email") must beEqualTo(None)
      }
    }
  }
}
