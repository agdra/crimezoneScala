package models.services

import javax.inject.Inject

import models.LaporanKejahatan
import models.daos.LaporanKejahatanDAO
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class LaporanKejahatanService @Inject()(lapKDAO: LaporanKejahatanDAO) {

  def list: Future[Seq[LaporanKejahatan]] = {
    lapKDAO.list
  }

  def findByID(id: Int): Future[Option[LaporanKejahatan]] = {
    lapKDAO.findByID(id)
  }

  def add(jenisK: LaporanKejahatan): Future[Option[LaporanKejahatan]] = {
    lapKDAO.insert(jenisK).map { Int =>
      Option(jenisK.copy(id = Option(Int)))
    }
  }

}
