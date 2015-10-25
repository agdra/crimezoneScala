package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Format, Json}
import slick.driver.JdbcProfile
import models.daos.GenericTable

case class WilayahKepolisian(id: Option[Int], wilayah: String)

object WilayahKepolisian {

  implicit val wilayahKF: Format[WilayahKepolisian] = Json.format[WilayahKepolisian]

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class WilayahKepolisianT(tag: Tag) extends GenericTable[WilayahKepolisian](tag, "wilayah_kepolisian") {

    override def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def wilayah = column[String]("wilayah")
    
    def * = (id.?, wilayah) <>
      ((WilayahKepolisian.apply _).tupled, WilayahKepolisian.unapply)
  }

  val table = TableQuery[WilayahKepolisianT]
}
