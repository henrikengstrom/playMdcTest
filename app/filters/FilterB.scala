package filters

import javax.inject._
import play.api.mvc._
import scala.concurrent.ExecutionContext
import org.slf4j.MDC

@Singleton
class FilterB @Inject()(implicit ec: ExecutionContext) extends EssentialFilter {
  override def apply(ea: EssentialAction) = EssentialAction { request =>
    if (MDC.getCopyOfContextMap ne null) println("filterB (inbound MDC): " + MDC.getCopyOfContextMap.toString)

    MDC.put("filterBTime", s"time-${System.nanoTime()}")

    ea(request).map { result =>
      result.withHeaders("X-ExampleFilter" -> "foo")
    }
  }
}