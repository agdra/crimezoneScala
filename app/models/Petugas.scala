package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Format, Json}
import slick.driver.JdbcProfile
import models.daos.GenericTable

case class Petugas(
  id: Option[Int],
  nrp: String,
  nama: String,
  email: String,
  password: String,
  idWilayahKepolisian: Int,
  idDaftarPelapor: Int)

object Petugas {

  implicit val petugasF: Format[Petugas] = Json.format[Petugas]

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class PetugasT(tag: Tag) extends GenericTable[Petugas](tag, "petugas") {

    override def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def nrp = column[String]("nrp")
    def nama = column[String]("nama")
    def email = column[String]("email")
    def password = column[String]("password")
    def idWilayahKepolisian = column[Int]("id_wilayah_kepolisian")
    def idDaftarPelapor = column[Int]("id_daftar_pelapor")

    def *  = (id.?, nrp, nama, email, password, idWilayahKepolisian, idDaftarPelapor) <> ((Petugas.apply _).tupled, Petugas.unapply)

    def fkWilayahKepolisian = foreignKey("fk1_wilayah_kepolisian", idWilayahKepolisian, WilayahKepolisian.table)(_.id)
    def fkDaftarPelapor = foreignKey("fk2_daftar_pelapor", idDaftarPelapor, DaftarPelapor.table)(_.id)
  }

  val table = TableQuery[PetugasT]
}
