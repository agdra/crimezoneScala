package controllers.api

import javax.inject.Inject
import models.KegiatanPolres
import models.services.KegiatanPolresService
import play.api.cache.Cached
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import utils.{SuccessResponse, ErrorResponse}
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class JsonKegiatanPolresControl @Inject()(kpService: KegiatanPolresService, val messagesApi: MessagesApi, cached: Cached)
  extends Controller with I18nSupport {

    def list = Action.async { implicit request =>
      kpService.list.map {
        case kp: Seq[models.KegiatanPolres] => Ok(Json.toJson(SuccessResponse(kp)))
        case _ => NotFound(Json.toJson(ErrorResponse(NOT_FOUND, messagesApi("jenis.kejahatan.not.found"))))
      }
    }

    def findByID(id: Int) = Action.async { implicit request =>
      kpService.findByID(id).map {
        case Some(kp) => Ok(Json.toJson(SuccessResponse(kp)))
        case None => NotFound(Json.toJson(ErrorResponse(NOT_FOUND, messagesApi("jenis.kejahatan.not.found"))))
      }
    }

  }
