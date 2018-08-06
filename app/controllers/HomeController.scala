package controllers

import javax.inject._
import org.slf4j.MDC
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  def index = Action {
    Ok(MDC.getCopyOfContextMap().toString)
  }
}