package controllers

import model._
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object HomeController extends Controller {

    case class Item(knowledge: String)

    implicit val createItem = Json.reads[Item]
    implicit val writeKnowledge = Json.writes[Knowledge]

    /**
     * Web service listening in GET / route. It returns a list of pieces of knowledge stored in the MySQL database.
     * 
     * @return it returns a Future[Action] object
     */
    def index = Action.async {
        Knowledges.listAll.map(res =>
            Ok(Json.toJson(res))
        )
    }

    /**
     * Web service listening in POST / route. It receives a json with a new piece of knowledge and store it in the
     * MySQL databse.
     *
     * @return it returns a Future[Action] object
     */
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
