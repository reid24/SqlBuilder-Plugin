import com.beckettit.sqlbuilder.Query;
import groovy.sql.Sql

class SqlBuilderService {

    boolean transactional = false
    javax.sql.DataSource dataSource

    //wrapper methods for Groovy Sql methods

    def eachRow(Query query, Closure closure) {
    	def sql = new Sql(dataSource)
    	sql.eachRow(query.toSql(), query.parameters, closure)
    }

    def rows(Query query){
    	def sql = new Sql(dataSource)
    	return sql.rows(query.toSql(), query.parameters)
    }
    
    def firstRow(Query query){
    	def sql = new Sql(dataSource)
    	return sql.firstRow(query.toSql(), query.parameters)
    }
    
}
