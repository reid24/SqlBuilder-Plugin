import com.beckettit.sqlbuilder.SqlBuilder;

class SqlBuilderGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Your name"
    def authorEmail = ""
    def title = "Plugin summary/headline"
    def description = '''\\
Brief description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/SqlBuilder+Plugin"

    def doWithSpring = {
    	println "SqlBuilder plugin: registering queries"
    	application.config.SqlBuilder.each {
    		SqlBuilder.registerQuery(it.key, it.value)
    		println "SqlBuilder plugin: registering query '${it.key}': \n\t${SqlBuilder.namedQuery(it.key).toSql()}"
    	}
    	println "SqlBuilder plugin: done registering queries"
    }

    def doWithApplicationContext = { applicationContext ->
    	def sqlBuilderService = applicationContext.getBean("sqlBuilderService")
    	application.config.SqlBuilder.each {
        	def queryName = it.key.toString()
        	sqlBuilderService.metaClass {
        		"${queryName}" { conditions ->
    				def instance = delegate
    				return SqlBuilder.namedQuery(queryName).build(conditions)
    			}
            }
        	println "SqlBuilder plugin: Registered method sqlBuilderService.${queryName}"
            println "\t" + sqlBuilderService."${queryName}"{}.toSql()
    	}

    }

    def doWithWebDescriptor = { xml ->
    }

    def doWithDynamicMethods = { ctx ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}
