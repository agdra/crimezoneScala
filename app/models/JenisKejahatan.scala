package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Format, Json}
import slick.driver.JdbcProfile
import models.daos.GenericTable

case class JenisKejahatan(id: Option[Int], nama: String, imgURL: Option[String])

object JenisKejahatan {

  implicit val jeniskF: Format[JenisKejahatan] = Json.format[JenisKejahatan]

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class JenisKejahatanT(tag: Tag) extends GenericTable[JenisKejahatan](tag, "jenis_kejahatan") {
    
    override def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def nama = column[String]("nama")
    def imgURL = column [String]("img_url")
    def * = (id.?, nama, imgURL.?) <> ((JenisKejahatan.apply _).tupled, JenisKejahatan.unapply)
  }

  val table = TableQuery[JenisKejahatanT]
}
