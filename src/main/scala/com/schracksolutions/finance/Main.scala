package com.schracksolutions.finance

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import cats.effect.unsafe.implicits.global
import com.schracksolutions.finance.data.ApplicationConfig

object Main extends IOApp {

  val logger = org.log4s.getLogger

  def run(args: List[String]): IO[ExitCode] = {
    def configuration: IO[ApplicationConfig] = {
      for {
        cfg <- ApplicationConfig.loadConfig[IO]
      } yield cfg
    }

    val config = configuration.unsafeRunSync()

    logger.info(s"Checking migrations on ${config.url} using ${config.user.getOrElse("")} ")

    FinanceServer.stream[IO].compile.drain.as(ExitCode.Success)
  }

}
