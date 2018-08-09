package controllers

import org.slf4j.MDC
import javax.inject.Inject

import play.api.mvc._
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class CustomRouter @Inject()(controller: HomeController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/test") =>
      MDC.put("customRouterTime", s"time-${System.nanoTime}")
      controller.index
  }
}