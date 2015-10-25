package models

import java.sql.{Date, Time}
import java.time.{LocalDate, LocalTime}
import utils.SlickMapping.{myDateColumnType, timeColumnType}
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Format, Json}
import slick.driver.JdbcProfile
import models.daos.GenericTable

case class LaporanKejahatan(
  id: Option[Int],
  idDaftarPelapor: Option[Int],
  idJenisKejahatan: Option[Int],
  idLokasiKejahatan: Option[Int],
  idStatusLaporan: Option[Int],
  judul: String,
  deskripsi: String,
  img_URL: String,
  tglKejadian: Option[LocalDate],
  waktuKejadian: Option[String],
  tglPenanganan: Option[LocalDate],
  alamat: String,
  latitude: Float,
  longitude: Float)

case class Tampung(id: Option[Int], judul: String, idDaftarPelapor: Option[Int])

object LaporanKejahatan {

  implicit val laporanKF: Format[LaporanKejahatan] = Json.format[LaporanKejahatan]

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class LaporanKejahatanT(tag: Tag) extends GenericTable[LaporanKejahatan](tag, "laporan_kejahatan") {

    override def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def idDaftarPelapor = column[Int]("id_daftar_pelapor")
    def idJenisKejahatan = column[Int]("id_jenis_kejahatan")
    def idLokasiKejahatan = column[Int]("id_lokasi_kejahatan")
    def idStatusLaporan = column[Int]("id_status_laporan")
    def judul = column[String]("judul")
    def deskripsi = column[String]("deskripsi")
    def imgURL = column[String]("img_url")
    def tglKejadian = column[LocalDate]("tanggal_kejadian")
    def waktuKejadian = column[String]("waktu")
    def tglPenanganan = column[LocalDate]("tanggal_penanganan")
    def alamat = column[String]("alamat")
    def latitude = column[Float]("latitude")
    def longitude = column[Float]("longitude")

    def * = (id.?, idDaftarPelapor.?, idJenisKejahatan.?, idLokasiKejahatan.?, idStatusLaporan.?,
      judul, deskripsi, imgURL, tglKejadian.?, waktuKejadian.?, tglPenanganan.?, alamat, latitude, longitude) <>
        ((LaporanKejahatan.apply _).tupled, LaporanKejahatan.unapply)

    def fkDaftarPelapor = foreignKey("fk3_daftar_pelapor", idDaftarPelapor, DaftarPelapor.table)(_.id)
    def fkJenisKejahatan = foreignKey("fk1_jenis_kejahatan", idDaftarPelapor, JenisKejahatan.table)(_.id)
    def fkLokasiKejahatan = foreignKey("fk1_lokasi_kejahatan", idDaftarPelapor, LokasiKejahatan.table)(_.id)
    def fkStatusLaporan = foreignKey("fk1_status_laporan", idDaftarPelapor, StatusLaporan.table)(_.id)
  }

  val table = TableQuery[LaporanKejahatanT]
}
