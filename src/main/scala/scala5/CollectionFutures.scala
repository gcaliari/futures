package scala5

import java.util.concurrent.Executors

import scala1.WaitTimes

import scala.concurrent.{Await, ExecutionContext, Future}

/**
  * Sequencing Futures
  */
object CollectionFutures extends App {

  private val threadPool = Executors.newFixedThreadPool(1)
  private implicit val executionContext = ExecutionContext.fromExecutorService(threadPool)

  val seq = 0 to 10

  val r = for {
    s <- seq
    if s % 2 == 0
  } yield for {
    _ <- Future { println(s"future 1 : $s - ThreadId=${Thread.currentThread().getId}") }
    _ = println("print 3")
    _ <- Future { println(s"future 2 : $s - ThreadId=${Thread.currentThread().getId}") }
  } yield s"$s"


  println(Await.result(Future.sequence(r), WaitTimes.time))

  threadPool.shutdown()
}
