package controllers

import javax.inject.Inject
import models.JenisKejahatan
import models.services.JenisKejahatanService
import play.api.cache.Cached
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import utils.{SuccessResponse, ErrorResponse}
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class JenisKejahatanControl @Inject()(jenisKS: JenisKejahatanService, val messagesApi: MessagesApi, cached: Cached)
  extends Controller with I18nSupport {

    def list = Action.async { implicit request =>
      jenisKS.list.map { jenisK =>
        Ok(views.html.jeniskejahatan.list())
      }
    }

  }
