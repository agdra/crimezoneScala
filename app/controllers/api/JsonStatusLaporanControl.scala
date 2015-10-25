package controllers.api

import javax.inject.Inject
import models.StatusLaporan
import models.services.StatusLaporanService
import play.api.cache.Cached
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import utils.{SuccessResponse, ErrorResponse}
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class JsonStatusLaporanControl @Inject()(slService: StatusLaporanService, val messagesApi: MessagesApi, cached: Cached)
  extends Controller with I18nSupport {

    def list = Action.async { implicit request =>
      slService.list.map {
        case sl: Seq[models.StatusLaporan] => Ok(Json.toJson(SuccessResponse(sl)))
        case _ => NotFound(Json.toJson(ErrorResponse(NOT_FOUND, messagesApi("status.laporan.not.found"))))
      }
    }

    def findByID(id: Int) = Action.async { implicit request =>
      slService.findByID(id).map {
        case Some(sl) => Ok(Json.toJson(SuccessResponse(sl)))
        case None => NotFound(Json.toJson(ErrorResponse(NOT_FOUND, messagesApi("status.laporan.not.found"))))
      }
    }

    def update = Action.async { implicit request =>
      val incomingStatusLaporan = StatusLaporan.formStatusLaporan.bindFromRequest()

      incomingStatusLaporan.fold({ error =>
        val response = ErrorResponse(BAD_REQUEST, messagesApi("request.error"))
        Future.successful(BadRequest(Json.toJson(response)))
      }, { statuslaporan =>
        slService.update(statuslaporan).map { resp =>
          if (resp == 1) {
            Created(Json.toJson(SuccessResponse(resp)))
          } else {
            BadRequest(Json.toJson(ErrorResponse(BAD_REQUEST, messagesApi("status.laporan.not.found"))))
          }
        }
      })
    }

  }
