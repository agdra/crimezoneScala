package controllers.api

import javax.inject.Inject
import forms.SignInForm
import models.User
import models.services.UserService
import play.api.cache.Cached
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller, BodyParsers}
import utils.{SuccessResponse, ErrorResponse}
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import security.Authenticated

class JsonUserControl @Inject()(userService: UserService, val messagesApi: MessagesApi, cached: Cached)
  extends Controller with I18nSupport {

    def login = Action.async(BodyParsers.parse.json) { implicit request =>
      val incomingSignIn = request.body.validate[SignInForm]

      incomingSignIn.fold({ error =>
        val response = ErrorResponse(BAD_REQUEST, messagesApi("request.errror"))
        Future.successful(BadRequest(Json.toJson(response)))
      }, { sign =>
        userService.validateUser(sign.email, sign.password).flatMap {
          case Some(user) => {
            Future.successful(Ok(Json.toJson(SuccessResponse(user))).withSession("email" -> user.email))
          }
          case None => {
            Future.successful(NotFound(Json.toJson(ErrorResponse(NOT_FOUND, messagesApi("user.not.found")))))
          }
        }
      })
    }

    def list = Action.async { implicit request =>
      val userFuture = userService.findListUser()
      userFuture.map {
        case user: Seq[models.User] => Ok(Json.toJson(SuccessResponse(user)))
        case _ => NotFound(Json.toJson(ErrorResponse(NOT_FOUND, messagesApi("user.not.found"))))
      }
    }

    def findByID(id: Int) = Action.async { implicit request =>
      userService.findByID(id).map {
        case Some(user) => Ok(Json.toJson(SuccessResponse(user)))
        case None => NotFound(Json.toJson(ErrorResponse(NOT_FOUND, messagesApi("user.not.found"))))
      }
    }

    def add = Action.async { implicit request =>
      val incomingUser = User.formUser.bindFromRequest()

      incomingUser.fold(error => {
        val response = ErrorResponse(BAD_REQUEST, messagesApi("request.error"))
        Future.successful(BadRequest(Json.toJson(response)))
      }, {newUser =>
        userService.addUser(newUser).flatMap {
          case Some(user) =>  Future.successful(Ok(Json.toJson(SuccessResponse(user))))
          case None => Future.successful(BadRequest(Json.toJson(ErrorResponse(NOT_FOUND, messagesApi("user.not.save")))))
        }
      })
    }

    def update = Action.async { implicit request =>
      val incomingUser = User.formUser.bindFromRequest()

      incomingUser.fold(error => {
        val response = ErrorResponse(BAD_REQUEST, messagesApi("request.error"))
        Future.successful(BadRequest(Json.toJson(response)))
      }, { newUser =>
        userService.updateUser(newUser).map { resp =>
          if (resp > 0) {
            Ok(Json.toJson(SuccessResponse(resp)))
          } else {
            BadRequest(Json.toJson(resp))
          }
        }
      })
    }

    def delete(id: Int) = Action.async { implicit request =>
      userService.deleteUser(id).map { resp =>
        if (resp == 1) {
          Ok(Json.toJson(SuccessResponse(resp)))
        } else {
          BadRequest(Json.toJson(ErrorResponse(BAD_REQUEST, messagesApi("user.not.found"))))
        }
      }
    }

  }
