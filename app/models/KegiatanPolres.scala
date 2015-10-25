package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Format, Json}
import slick.driver.JdbcProfile
import models.daos.GenericTable

import java.sql.{Date, Time}
import java.time.{LocalDate, LocalTime}
import utils.SlickMapping.{myDateColumnType, timeColumnType}

case class KegiatanPolres(
  id: Option[Int], idPetugas: Int, judul: String, deskripsi: String,
  tanggal: LocalDate, waktu: String, alamat: String)

object KegiatanPolres {

  implicit val kegPolresF: Format[KegiatanPolres] = Json.format[KegiatanPolres]

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class KegiatanPolresT(tag: Tag) extends GenericTable[KegiatanPolres](tag, "kegiatan_polrestabes") {

    override def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def idPetugas = column[Int]("id_petugas")
    def judul = column[String]("judul")
    def deskripsi = column[String]("deskripsi")
    def tanggal = column[LocalDate]("tanggal")
    def waktu = column[String]("waktu")
    def alamat = column[String]("alamat")

    def * = (id.?, idPetugas, judul, deskripsi, tanggal, waktu, alamat) <>
      ((KegiatanPolres.apply _).tupled, KegiatanPolres.unapply)

    def fkPetugas = foreignKey("fk1_petugas", idPetugas, Petugas.table)(_.id)
  }

  val table = TableQuery[KegiatanPolresT]
}
