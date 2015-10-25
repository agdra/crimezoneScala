package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Json, Format}
import slick.driver.JdbcProfile
import models.daos.GenericTable

case class DaftarPelapor(id: Option[Int], idJenisPelapor: Int)

object DaftarPelapor {

  implicit val DaftarPF: Format[DaftarPelapor] = Json.format[DaftarPelapor]

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class DaftarPelaporT(tag: Tag) extends GenericTable[DaftarPelapor](tag, "daftar_pelapor") {

    override def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def idJenisPelapor = column[Int]("id_jenis_pelapor")
    def * = (id.?, idJenisPelapor) <> ((DaftarPelapor.apply _).tupled, DaftarPelapor.unapply)
    def fkJenisPelapor = foreignKey("fk_daftar_pelapor", idJenisPelapor, JenisPelapor.table)(_.id)
  }

  val table = TableQuery[DaftarPelaporT]
}
