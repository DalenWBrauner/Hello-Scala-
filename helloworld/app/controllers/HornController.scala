package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class HornController @Inject() extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.horn_index())
  }

  def popup = Action { implicit request =>
    Ok(views.html.horn_popup_original())
  }

  def widget = Action { implicit request =>
    Ok(views.html.horn_widget_original())
  }
}
