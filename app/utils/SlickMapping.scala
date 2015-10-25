package utils

import java.sql.{Date, Time}
import java.time.{LocalDate, LocalTime, LocalDateTime, ZoneOffset}
import slick.driver.PostgresDriver.api._
import slick.driver.PostgresDriver.api.{ timeColumnType => DefaultTimeColumnType }

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import play.api.Play.current

object SlickMapping {

  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](current)
  import dbConfig.driver.api._

  implicit val myDateColumnType = MappedColumnType.base[LocalDate, Date](
      ld => Date.valueOf(ld),
      d => d.toLocalDate
  )

  implicit val timeColumnType = MappedColumnType.base[LocalTime, Time](
      localTime => Time.valueOf(localTime),
      time => time.toLocalTime
  )
}
