package utilities

import javaposse.jobdsl.dsl.Context;


public class BUILD_JOB implements Context {
    private String Project_version
    private def jdk_x
    private String propertiesFile
    private String Name_Proyect
    private String Project_Version
    private String deploy_stage
	private String ant_home
	private String fileBuild


	static void addBUILD_WEB_JOB(def job, def jdk_x, String propertiesFile, String Name_Proyect,String Project_Version, String deploy_stage, String fileBuild, ant_home){
	def prop_stage_build ='''\
    PROJECT_NAME='''+Name_Proyect+'''-'''+Project_Version+'''
    DEPLOY_STAGE= '''+deploy_stage+'''
    '''.stripIndent()
			 
			 job.with{
			 jdk('JAVA_HOME')
			 steps {
			     envInjectBuilder {
			         propertiesFilePath(propertiesFile)
			         propertiesContent(prop_stage_build)
			     }
			     ant {
			         target('build')
			         buildFile(fileBuild)
			         antInstallation(ant_home)
			     }
			 }

			 }
			 }
			 
			 
			 
}