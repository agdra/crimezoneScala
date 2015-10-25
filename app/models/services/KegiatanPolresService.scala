package models.services

import javax.inject.Inject

import models.KegiatanPolres
import models.daos.KegiatanPolresDAO
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class KegiatanPolresService @Inject()(kpDAO: KegiatanPolresDAO) {

  def list: Future[Seq[KegiatanPolres]] = {
    kpDAO.list
  }

  def findByID(id: Int): Future[Option[KegiatanPolres]] = {
    kpDAO.findByID(id)
  }

  def add(kp: KegiatanPolres): Future[Option[KegiatanPolres]] = {
    kpDAO.insert(kp).map { Int =>
      Option(kp.copy(id = Option(Int)))
    }
  }

}
