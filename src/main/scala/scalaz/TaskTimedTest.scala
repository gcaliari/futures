package scalaz

import java.util.concurrent.{ExecutorService, Executors}

import scalaz.concurrent.Task

object TaskTimedTest extends App {

  private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

  def printAndReturn(s: String): String = {
    println(s"$s - ThreadId=${Thread.currentThread().getId}")
    Thread.sleep(2000)
    s
  }

  import scala.concurrent.duration._


  println(s"Start - ThreadId=${Thread.currentThread().getId}")
  val t1: String = Task(printAndReturn("aaa"))(threadPool)
    .timed(3 second)
    .map(v => printAndReturn(v + "bbb"))
    .unsafePerformSync

  println(t1)
  threadPool.shutdown()
}