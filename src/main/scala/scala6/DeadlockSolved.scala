package scala6

import java.util.concurrent.Executors

import scala1.WaitTimes

import scala.concurrent.{Await, ExecutionContext, Future}

/**
  * Deadlock solved
  */
object DeadlockSolved extends App {

  private val threadPool = Executors.newFixedThreadPool(1)
  private implicit val executionContext = ExecutionContext.fromExecutorService(threadPool)
  private val threadPool2 = Executors.newFixedThreadPool(1)
  private val executionContext2 = ExecutionContext.fromExecutorService(threadPool2)

  val r = for {
    i <- Future{ fut }
  } yield i

  private def fut = {
    Await.result(Future{1 + 1}(executionContext2), WaitTimes.time)
  }

  println(Await.result(r, WaitTimes.time))

  threadPool2.shutdown()
  threadPool.shutdown()
}
