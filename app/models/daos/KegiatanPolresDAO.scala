package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.lifted.TableQuery

import models.KegiatanPolres
import models.KegiatanPolres.KegiatanPolresT

class KegiatanPolresDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends GenericCRUD[KegiatanPolresT, KegiatanPolres] {

    override val table = TableQuery[KegiatanPolresT]
  }
