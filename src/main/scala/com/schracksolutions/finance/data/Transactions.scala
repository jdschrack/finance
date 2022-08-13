package com.schracksolutions.finance.data

import cats.Applicative
import cats.effect.Concurrent
import cats.implicits.catsSyntaxApplicativeId
import io.circe.Decoder
import io.circe.Encoder
import io.circe.generic.semiauto._
import org.http4s._
import org.http4s.circe._

import java.time.LocalDateTime

trait Transactions[F[_]] {

  def get: F[Transactions.Transaction]

}

object Transactions {

  def apply[F[_]](implicit ev: Transactions[F]): Transactions[F] = ev

  def impl[F[_]: Applicative]: Transactions[F] = new Transactions[F] {

    def get: F[Transactions.Transaction] = Transaction("1", 224.24, LocalDateTime.now).pure[F]

  }

  final case class Transaction(id: String, amount: BigDecimal, date: LocalDateTime)

  final case class JokeError(e: Throwable) extends RuntimeException

  object Transaction {

    implicit def transactionDecoder: Decoder[Transaction] = deriveDecoder[Transaction]

    implicit def transactionEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, Transaction] =
      jsonOf

    implicit def transactionEncoder: Encoder[Transaction] = deriveEncoder[Transaction]

    implicit def transactionEntityEncoder[F[_]]: EntityEncoder[F, Transaction] =
      jsonEncoderOf

  }

}
