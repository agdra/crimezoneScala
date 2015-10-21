package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.lifted.TableQuery

import models.LokasiKejahatan
import models.LokasiKejahatan.LokasiKejahatanT

class LokasiKejahatanDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends GenericCRUD[LokasiKejahatanT, LokasiKejahatan] {
    override val table = TableQuery[LokasiKejahatanT]
  }
