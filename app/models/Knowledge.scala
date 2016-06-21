package model

import play.api.db.slick.DatabaseConfigProvider
import play.api.Play
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._

case class Knowledge(id: Long, knowledge: String)

class KnowledgeTableDef(tag: Tag) extends Table[Knowledge](tag, "knowledge") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def knowledge = column[String]("knowledge")

    override def * = (id, knowledge) <>(Knowledge.tupled, Knowledge.unapply)

}

object Knowledges {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val knowledges = TableQuery[KnowledgeTableDef]

    def add(knowledge: Knowledge): Future[String] = {
        dbConfig.db.run(knowledges += knowledge).map(res => "Added").recover {
            case ex: Exception => ex.getCause.getMessage
        }
    }

    def listAll: Future[Seq[Knowledge]] = {
        dbConfig.db.run(knowledges.result)
    }

}