package scala6

import java.util.concurrent.Executors

import scala1.WaitTimes

import scala.concurrent.{Await, ExecutionContext, Future}


/**
  * Deadlock
  */
object Deadlock extends App {

  private val threadPool = Executors.newFixedThreadPool(2)
  private implicit val executionContext = ExecutionContext.fromExecutorService(threadPool)

  val r = for {
    i <- Future{ fut }
  } yield i

  private def fut = {
    Await.result(Future{1 + 1}, WaitTimes.time)
  }

  println(Await.result(r, WaitTimes.time))

  threadPool.shutdown()
}
