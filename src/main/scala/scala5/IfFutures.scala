package scala5

import java.util.concurrent.Executors

import scala.concurrent.{Await, ExecutionContext, Future}
import scala1.WaitTimes

/**
  * If Futures
  */
object IfFutures extends App {

  private val threadPool = Executors.newFixedThreadPool(1)
  private implicit val executionContext = ExecutionContext.fromExecutorService(threadPool)

  val seq = 0 to 10

  val r = for {
    s <- seq
    if s % 2 == 0
  } yield for {
    _ <- Future { println(s"future 1 : $s - ThreadId=${Thread.currentThread().getId}") }
    r <- if(s > 2) Future { s * 100 } else Future.successful(s)
  } yield s"$r"


  println(Await.result(Future.sequence(r), WaitTimes.time))

  threadPool.shutdown()
}
