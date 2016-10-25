package controllers

import play.api.mvc._

/**
 * Created by septechuser on 24/10/2016.
 */
trait Secured {
  def email(request: RequestHeader) = request.session.get("email")

  def onUnauthorized(request: RequestHeader) = {
    Results.Redirect("/login").withNewSession
  }

  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(email, onUnauthorized) { email =>
      Action(request => f(email)(request))
    }
  }
}
