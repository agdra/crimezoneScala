package models.services

import javax.inject.Inject

import models.{LokasiKejahatan,Tampung}
import models.daos.LokasiKejahatanDAO
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class LokasiKejahatanService @Inject()(laporanKDAO: LokasiKejahatanDAO) {

  def list: Future[Seq[LokasiKejahatan]] = {
    laporanKDAO.list
  }

  def findByID(id: Int): Future[Option[LokasiKejahatan]] = {
    laporanKDAO.findByID(id)
  }

  def add(lokasiK: LokasiKejahatan): Future[Option[LokasiKejahatan]] = {
    laporanKDAO.insert(lokasiK).map { Int =>
      Option(lokasiK.copy(id = Option(Int)))
    }
  }

}
