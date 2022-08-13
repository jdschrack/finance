package com.schracksolutions.finance

import cats.effect.Sync
import cats.implicits._
import com.schracksolutions.finance.data.Transactions
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object FinanceRoutes {

  def transactionRoutes[F[_]: Sync](T: Transactions[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / "transactions" =>
      for {
        transactions <- T.get
        resp         <- Ok(transactions)
      } yield resp
    }
  }

  def jokeRoutes[F[_]: Sync](J: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / "joke" =>
      for {
        joke <- J.get
        resp <- Ok(joke)
      } yield resp
    }
  }

  def helloWorldRoutes[F[_]: Sync](H: HelloWorld[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / "hello" / name =>
      for {
        greeting <- H.hello(HelloWorld.Name(name))
        resp     <- Ok(greeting)
      } yield resp
    }
  }

}
