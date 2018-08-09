package controllers

import javax.inject._
import org.slf4j.MDC
import play.api.mvc._

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  def index = Action {
    Ok(MDC.getCopyOfContextMap().toString)
  }

  def futureIndex = Action.async {
    Future.apply(2+2).map(_ => futureComputation(System.nanoTime())) map { x =>
      Ok(MDC.getCopyOfContextMap().toString)
    }
  }

  def futureComputation(time: Long) : Future[Unit] = {
    MDC.put("futureComputationTime", s"time-$time")
    Future.unit
  }

}