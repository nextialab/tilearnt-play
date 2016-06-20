package controllers

import play.api.mvc._
import play.api.libs.json.{__, Reads}

object HomeController extends Controller {

    case class Knowledge(knowledge: String)

    implicit val createKnowledge = Json.reads[Knowledge]

    def index = Action {
        Ok("Hello world!")
    }

    def create = Action(parse.json) { implicit request =>
    	request.body.validate[Knowledge] match {
	    case JsSuccess(knowledge, _) =>
	    	 // Add to DB
	    case JsError(errors) =>
	    	 BadRequest
    }

}