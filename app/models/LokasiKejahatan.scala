package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Format, Json}
import slick.driver.JdbcProfile
import models.daos.GenericTable

case class LokasiKejahatan(id: Option[Int], nama: String)

object LokasiKejahatan {

  implicit val lkF: Format[LokasiKejahatan] = Json.format[LokasiKejahatan]

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class LokasiKejahatanT(tag: Tag) extends GenericTable[LokasiKejahatan](tag, "lokasi_kejahatan") {
    override def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def nama = column[String]("nama")
    def * = (id.?, nama) <> ((LokasiKejahatan.apply _).tupled, LokasiKejahatan.unapply)
  }
  val table = TableQuery[LokasiKejahatanT]
}
