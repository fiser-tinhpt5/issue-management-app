package models.user

import java.security.MessageDigest
import java.util.Base64

/**
 * Created by septechuser on 26/10/2016.
 */
object Password {
  val HashAlgorithm = "SHA-256"

  def encrypt(password: String): String = {
    MessageDigest.getInstance(HashAlgorithm).digest(password.getBytes()).map("%02x".format(_)).mkString
  }

}
