package models.issue

import java.util.Date

/**
 * Created by septechuser on 11/10/2016.
 */
case class Issue(id: Int, issue: String, challenge: String, raisedDate: Date, status: String = IssueStatus.NOT_YET)