package controllers

import model._
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object HomeController extends Controller {

    case class Item(knowledge: String)

    implicit val createItem = Json.reads[Item]

    def index = Action {
        Ok("Hello world!")
    }

    def create = Action.async(parse.json) { implicit request =>
    	request.body.validate[Item] match {
	    case JsSuccess(item, _) => {
                 val knowledge = Knowledge(0, item.knowledge)
	    	 Knowledges.add(knowledge).map(res =>
                     Ok("Added")
                 )
            }
	    case JsError(errors) => {
	    	 Future.successful(BadRequest)
            }
        }
    }

}