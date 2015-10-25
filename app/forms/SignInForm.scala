package forms

import play.api.data.Form
import play.api.data.Forms.{mapping, email, nonEmptyText}
import play.api.libs.json.{Json, Format}

case class SignInForm(email: String, password: String)

object SignInForm {

  implicit val SignIn: Format[SignInForm] = Json.format[SignInForm]

  val form = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(SignInForm.apply)(SignInForm.unapply))
}
