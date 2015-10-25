package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.lifted.TableQuery

import models.StatusLaporan
import models.StatusLaporan.StatusLaporanT

class StatusLaporanDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends GenericCRUD[StatusLaporanT, StatusLaporan] {

    override val table = TableQuery[StatusLaporanT]
  }
