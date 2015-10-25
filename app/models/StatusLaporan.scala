package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Format, Json}
import slick.driver.JdbcProfile
import models.daos.GenericTable

case class StatusLaporan(id: Option[Int], status: String)

object StatusLaporan {

  import play.api.data.Form
  import play.api.data.Forms.{mapping, nonEmptyText, optional, number}

  implicit val statusLF: Format[StatusLaporan] = Json.format[StatusLaporan]
  val formStatusLaporan = Form(
    mapping(
      "id" -> optional(number),
      "status" -> nonEmptyText
    )(StatusLaporan.apply)(StatusLaporan.unapply))

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class StatusLaporanT(tag: Tag) extends GenericTable[StatusLaporan](tag, "status_laporan") {

    override def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def status = column[String]("status")
    def * = (id.?, status) <> ((StatusLaporan.apply _).tupled, StatusLaporan.unapply)
  }

  val table = TableQuery[StatusLaporanT]
}
