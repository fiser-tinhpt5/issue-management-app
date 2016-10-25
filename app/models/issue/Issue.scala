package models.issue

import java.util.Date

import models.issue.Status.NOT_YET

/**
 * Created by septechuser on 11/10/2016.
 */
case class Issue(id: Int, issue: String, challenge: String, raisedDate: Date, status: Status = NOT_YET)

object Issue {
  def fromFormValue(issue: String, challenge: String): Issue = Issue(
    0,
    issue,
    challenge,
    new Date(),
    NOT_YET
  )

  def toFormValue(issue: Issue): Option[(String, String)] = {
    Some(issue.issue, issue.challenge)
  }
}
