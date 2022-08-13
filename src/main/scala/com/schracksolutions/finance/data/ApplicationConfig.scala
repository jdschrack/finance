package com.schracksolutions.finance.data

import cats.effect.Sync
import com.typesafe.config.ConfigFactory
import pureconfig.generic.semiauto.deriveConvert
import pureconfig.ConfigReader
import pureconfig.ConfigSource

final case class ApplicationConfig(
    url: String,
    driver: String,
    user: Option[String],
    password: Option[String],
    migrationsTable: String,
    migrationsLocations: List[String],
    port: Option[String],
    database: Option[String]
)

object ApplicationConfig {

  implicit val configReader: ConfigReader[ApplicationConfig] = deriveConvert

  def loadConfig[F[_]](implicit S: Sync[F]): F[ApplicationConfig] = S.delay {
    val config = ConfigFactory.load()
    ConfigSource.fromConfig(config).at("com.schracksolutions.finance.database-config").loadOrThrow[ApplicationConfig]
  }

}
