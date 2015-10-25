package models.daos

import javax.inject.Inject

import play.api.{Logger}
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.PostgresDriver.api._
import slick.lifted.TableQuery
import scala.concurrent.Future

import models.User
import models.User.UserT

class UserDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends GenericCRUD[UserT, User] {

    override val table = TableQuery[UserT]

    def findByEmail(email: String): Future[Option[User]] = {
      val findEmail = table.filter(_.email === email)
      Logger.info(s"Query findById: ${findEmail.result.statements}")
      db.run(findEmail.result.headOption)
    }
  }
