package models.services

import javax.inject.Inject

import models.StatusLaporan
import models.daos.StatusLaporanDAO
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class StatusLaporanService @Inject()(slDAO: StatusLaporanDAO) {

  def list: Future[Seq[StatusLaporan]] = {
    slDAO.list
  }

  def findByID(id: Int): Future[Option[StatusLaporan]] = {
    slDAO.findByID(id)
  }

  def add(sl: StatusLaporan): Future[Option[StatusLaporan]] = {
    slDAO.insert(sl).map { Int =>
      Option(sl.copy(id = Option(Int)))
    }
  }

  def update(sl: StatusLaporan): Future[Int] = {
    slDAO.update(sl.id.get, sl)
  }

}
