package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.lifted.TableQuery
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import models.{LaporanKejahatan, DaftarPelapor, Tampung}
import models.LaporanKejahatan.LaporanKejahatanT


class LaporanKejahatanDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends GenericCRUD[LaporanKejahatanT, LaporanKejahatan] {

    override val table = TableQuery[LaporanKejahatanT]
/*
    def toCaseClass(tampung: (Option[Int], String, Option[Int])) = Tampung.tupled(tampung)

    def listQuery(): Future[toCaseClass] = {
      val q = for {
        lapk <- LaporanKejahatan.table
        dp <- DaftarPelapor.table if dp.id === lapk.idDaftarPelapor
      } yield (lapk.id, lapk.judul, dp.id)
      db.run(q.result)
    }
*/
  }
