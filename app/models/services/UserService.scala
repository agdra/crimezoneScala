package models.services

import javax.inject.Inject

import models.User
import models.daos.UserDAO
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

import utils.Base64

class UserService @Inject()(userDAO: UserDAO) {

  def findListUser(): Future[Seq[User]] = {
    userDAO.list
  }

  def findByID(id: Int): Future[Option[User]] = {
    userDAO.findByID(id)
  }

  def addUser(user: User): Future[Option[User]] = {
    val newUser = user.copy(password = Base64.encodeString(user.password))
    userDAO.insert(newUser).map { Int =>
      val newUser = user.copy(id = Option(Int))
      Option(newUser)
    }
  }

  def updateUser(user: User): Future[Int] = {
    userDAO.update(user.id.get, user)
  }

  def deleteUser(id: Int): Future[Int] = {
    userDAO.delete(id)
  }

  def findUserByEmail(email: String): Future[Option[User]] = {
    userDAO.findByEmail(email)
  }

  def validateUser(email: String, password: String): Future[Option[User]] = {
    userDAO.findByEmail(email).map { user =>
      user.filter(u => u.email.equalsIgnoreCase(Base64.encodeString(password)))
    }
  }

}
