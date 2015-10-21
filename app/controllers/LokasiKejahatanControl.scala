package controllers

import javax.inject.Inject
import models.LokasiKejahatan
import models.services.LokasiKejahatanService
import play.api.cache.Cached
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import utils.{SuccessResponse, ErrorResponse}
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class LokasiKejahatanControl @Inject()(lkService: LokasiKejahatanService, val messagesApi: MessagesApi, cached: Cached)
  extends Controller with I18nSupport {
    def list = Action.async { implicit request =>
      lkService.list.map {
        case lk: Seq[models.LokasiKejahatan] => Ok(Json.toJson(SuccessResponse(lk)))
        case _ => NotFound(Json.toJson(ErrorResponse(NOT_FOUND, messagesApi("lokasi.kejahatan.not.found"))))
      }
    }

    def findByID(id: Int) = Action.async { implicit request =>
      lkService.findByID(id).map {
        case Some(lk) => Ok(Json.toJson(SuccessResponse(lk)))
        case None => NotFound(Json.toJson(ErrorResponse(NOT_FOUND, messagesApi("lokasi.kejahatan.not.found"))))
      }
    }

  }
