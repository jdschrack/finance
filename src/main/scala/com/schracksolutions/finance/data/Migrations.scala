package com.schracksolutions.finance.data

import cats.effect.kernel.Sync
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.Location

object Migrations {

  def makeMigrations[F[_]: Sync](config: ApplicationConfig): F[Int] = {
    Sync[F].delay {
      Flyway.configure
        .dataSource(config.url, config.user.getOrElse(""), config.password.getOrElse(""))
        .locations(config.migrationsLocations.map(new Location(_)).toList: _*)
        .table(config.migrationsTable)
        .outOfOrder(false)
        .load()
        .migrate()
        .migrationsExecuted
    }
  }

}
