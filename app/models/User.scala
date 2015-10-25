package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Format, Json}
import slick.driver.JdbcProfile
import models.daos.GenericTable

case class User(
  id: Option[Int],
  nama: String,
  email: String,
  password: String,
  imgURL: String,
  idDaftarPelapor: Option[Int])

object User {

  import play.api.data.Form
  import play.api.data.Forms.{mapping, email, nonEmptyText, optional, number}

  implicit val userF: Format[User] = Json.format[User]
  val formUser = Form(
    mapping(
      "id" -> optional(number),
      "nama" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText(minLength = 3),
      "imgURL" -> nonEmptyText,
      "idDaftarPelapor" -> optional(number)
    )(User.apply)(User.unapply))

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class UserT(tag: Tag) extends GenericTable[User](tag, "pengguna") {

    override def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def nama = column[String]("nama")
    def email = column[String]("email")
    def password = column[String]("password")
    def imgURL = column[String]("img_url")
    def idDaftarPelapor = column[Int]("id_daftar_pelapor")

    def *  = (id.?, nama, email, password, imgURL, idDaftarPelapor.?) <>
      ((User.apply _).tupled, User.unapply)

    def fkDaftarPelapor = foreignKey("fk1_daftar_pelapor", idDaftarPelapor, DaftarPelapor.table)(_.id)
  }

  val table = TableQuery[UserT]
}
