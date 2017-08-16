package scalaz

import java.util.concurrent.{ExecutorService, Executors}

import scalaz.concurrent.Task

object TaskRetryTest extends App {

  private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

  def printAndReturn(s: String): String = {
    println(s"$s - ThreadId=${Thread.currentThread().getId}")
    s
  }

  var count = 0

  def randomException: String = {
    count += 1
    count match {
      case 1 => throw new IllegalArgumentException()
      case 2 => throw new ArithmeticException()
      case 3 => printAndReturn("aaa")
    }
  }

  import scala.concurrent.duration._

  private val throwableToBoolean: Throwable => Boolean = {
    case e: ArithmeticException => {
      println(e)
      true
    }
  }

  println(s"Start - ThreadId=${Thread.currentThread().getId}")
  val t1: String = Task(randomException)(threadPool)
    .retry(Seq(2 seconds), throwableToBoolean)
    .map(v => printAndReturn(v + "bbb"))
    .unsafePerformSync

  println(t1)
  threadPool.shutdown()
}