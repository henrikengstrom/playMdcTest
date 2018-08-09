package filters

import javax.inject._
import play.api.mvc._
import scala.concurrent.ExecutionContext
import org.slf4j.MDC
import scala.concurrent.Future
import akka.stream.Materializer

class FilterA @Inject() (implicit val mat: Materializer, ec: ExecutionContext) extends Filter {
  def apply(nextFilter: RequestHeader => Future[Result])
           (requestHeader: RequestHeader): Future[Result] = {

    if (MDC.getCopyOfContextMap ne null) println("filterA (inbound MDC): " + MDC.getCopyOfContextMap.toString)

    MDC.put("filterATime", s"time-${System.nanoTime}")

    nextFilter(requestHeader) map { result =>
      println(s"filterA: (returning result): ${MDC.getCopyOfContextMap.toString}\n-----------------------------------------------------")
      result
    }
  }
}