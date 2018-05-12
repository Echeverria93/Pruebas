package utilities

import javaposse.jobdsl.dsl.Context;


public class BUILD_JOB implements Context {
    private String Project_version
    private String Jdk_x
    private String propertiesFile
    private String Name_Proyect
    private String Project_Version
    private String deploy_stage


	static void addBUILD_WEB_JOB(def job, String Jdk_x, String propertiesFile, String Name_Proyect,String Project_Version, String deploy_stage){
	         
	def prop_stage_build ='''\
    PROJECT_NAME='''+Name_Proyect+'''-'''+Project_Version+'''
    DEPLOY_STAGE= '''+deploy_stage+'''
    '''.stripIndent()
			 
			 logRotator(1, 5, 1, 5)
			 jdk(Jdk_x)
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