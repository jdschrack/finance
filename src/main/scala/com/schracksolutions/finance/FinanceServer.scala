package com.schracksolutions.finance

import cats.effect.Async
import cats.effect.Resource
import cats.syntax.all._
import com.comcast.ip4s._
import com.schracksolutions.finance.data.Transactions
import fs2.Stream
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.Logger

object FinanceServer {

  def stream[F[_]: Async]: Stream[F, Nothing] = {
    for {
      client         <- Stream.resource(EmberClientBuilder.default[F].build)
      helloWorldAlg   = HelloWorld.impl[F]
      jokeAlg         = Jokes.impl[F](client)
      transactionsAlg = Transactions.impl[F]

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract segments not checked
      // in the underlying routes.
      httpApp = (
                  FinanceRoutes.transactionRoutes[F](transactionsAlg) <+>
                    FinanceRoutes.helloWorldRoutes[F](helloWorldAlg) <+>
                    FinanceRoutes.jokeRoutes[F](jokeAlg)
                ).orNotFound

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      exitCode <- Stream.resource(
                    EmberServerBuilder
                      .default[F]
                      .withHost(ipv4"0.0.0.0")
                      .withPort(port"8080")
                      .withHttpApp(finalHttpApp)
                      .build >>
                      Resource.eval(Async[F].never)
                  )
    } yield exitCode
  }.drain

}
