package utilities

import javaposse.jobdsl.dsl.Context;


public class SONARQUBE_JOB implements Context {

private String Project_Version
private String Name_Proyect


static void addSONARQUBE_WEB(def job,String Name_Proyect, String Project_Version ){

def sonarProperties ='''\
 sonar.projectKey='''+Name_Proyect+'''
 sonar.projectName='''+Name_Proyect+'''-'''+Project_Version+'''
 sonar.projectVersion='''+Project_Version+''' 
 sonar.sources=${WORKSPACE}
 sonar.exclusions=**/Scripts/**
 sonar.java.binaries=${WORKSPACE}/'''+Name_Proyect+'''-'''+Project_Version+'''/dist
 sonar.language=java
 '''.stripIndent()


			 job.with{
			 jdk('JAVA_HOME')
			 configure {
                 node ->
                     node / builders / 'hudson.plugins.sonar.SonarRunnerBuilder' {
                         project('')
                         properties(sonarProperties)
                         additionalArguments('')
                         jdk('Inherit From Job')
                         task('')
                     }
             }
             configure {
                 project ->
                     project / publishers << 'org.quality.gates.jenkins.plugin.QGPublisher' {
                         jobConfigData {
                             projectKey(Name_Proyect)
                             //sonarInstanceName('SonarQube')                
                         }

                     }
             }

			 }
			 
			 }
			 
			 
			 
static void addSONARQUBE_ROBOT(def job,String Name_Proyect, String Project_Version ){

def sonarProperties ='''\
 sonar.projectKey='''+Name_Proyect+'''
 sonar.projectName='''+Name_Proyect+'''-'''+Project_Version+'''
 sonar.projectVersion='''+Project_Version+''' 
 sonar.sources=${WORKSPACE}
 sonar.exclusions=**/Scripts/**
 sonar.java.binaries=${WORKSPACE}
 sonar.language=java
 '''.stripIndent()


			 job.with{
			 jdk('JAVA_HOME')
             configure {
                node ->
                     node / builders / 'hudson.plugins.sonar.SonarRunnerBuilder' {
                         project('')
                         properties(sonarProperties)
                         additionalArguments('')
                         jdk('Inherit From Job')
                         task('')
                     }
             }

             configure {
                 project ->
                     project / publishers << 'org.quality.gates.jenkins.plugin.QGPublisher' {
                         jobConfigData {
                             projectKey(project_name)
                             //sonarInstanceName('SonarQube')                
                         }

                     }
             }

			 }


}




}