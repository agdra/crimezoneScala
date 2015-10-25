package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Format, Json}
import slick.driver.JdbcProfile
import models.daos.GenericTable

case class JenisPelapor(id: Int, nama: String)

object JenisPelapor {

  implicit val JenisPF: Format[JenisPelapor] = Json.format[JenisPelapor]

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class JenisPelaporT(tag: Tag) extends GenericTable[JenisPelapor](tag, "jenis_pelapor") {

    override def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def nama = column[String]("nama")
    def * = (id, nama) <> ((JenisPelapor.apply _).tupled, JenisPelapor.unapply)
  }
  
  val table = TableQuery[JenisPelaporT]
}
