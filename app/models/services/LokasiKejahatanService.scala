package models.services

import javax.inject.Inject

import models.LokasiKejahatan
import models.daos.LokasiKejahatanDAO
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class LokasiKejahatanService @Inject()(lkDAO: LokasiKejahatanDAO) {

  def list: Future[Seq[LokasiKejahatan]] = {
    lkDAO.list
  }

  def findByID(id: Int): Future[Option[LokasiKejahatan]] = {
    lkDAO.findByID(id)
  }

  def add(lokasiK: LokasiKejahatan): Future[Option[LokasiKejahatan]] = {
    lkDAO.insert(lokasiK).map { Int =>
      Option(lokasiK.copy(id = Option(Int)))
    }
  }

}
