package model

import play.api.db.slick.DatabaseConfigProvider
import play.api.Play
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._

case class Knowledge(id: Long, knowledge: String)

/**
 * Represents the MySQL "knowledge" table as a class
 */
class KnowledgeTableDef(tag: Tag) extends Table[Knowledge](tag, "knowledge") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def knowledge = column[String]("knowledge")

    override def * = (id, knowledge) <>(Knowledge.tupled, Knowledge.unapply)

}

/**
 * Interface to query the MySQL "knowledge" table
 */
object Knowledges {

    /**
     * Load and store the current database configuration
     */
    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    /**
     * Creates a query channel
     */
    val knowledges = TableQuery[KnowledgeTableDef]

    /**
     * Adds a new piece of knowledge to the "knowledge" table
     * 
     * @param knowledge a new piece of knowledge to be stores in the table
     * @return Future[String] a future that can be resolved to success or failure
     */
    def add(knowledge: Knowledge): Future[String] = {
        dbConfig.db.run(knowledges += knowledge).map(res => "Added").recover {
            case ex: Exception => ex.getCause.getMessage
        }
    }

    /**
     * Query the table to list all the pieces of knowledge in the table
     *
     * @return Future[Seq[Knowledge]] a future that can resolve to a list of knowledge or a failure
     */
    def listAll: Future[Seq[Knowledge]] = {
        dbConfig.db.run(knowledges.result)
    }

}