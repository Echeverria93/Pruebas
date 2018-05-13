package utilities

import javaposse.jobdsl.dsl.Context;


public class PMD_JOB implements Context {

    private String Project_version
    private def jdk_x
    private String propertiesFile
    private String Name_Proyect
    private String Project_Version
    private String deploy_stage
	private String ant_home
	private String fileBuild
	private String correoJP
	private String item
	private def Patch_Workspace
	

static void addPMD_WEB(def job, def jdk_x,String Name_Proyect,String Project_Version, String deploy_stage, String fileBuild, String ant_home, String propertiesFile){

	def prop_stage_build ='''\
PROJECT_NAME='''+Name_Proyect+'''-'''+Project_Version+'''
DEPLOY_STAGE= '''+deploy_stage+'''
'''.stripIndent()

			 job.with{
			 jdk(jdk_x)

	            steps {
	                envInjectBuilder {
	                    propertiesFilePath(propertiesFile)
	                    propertiesContent(prop_stage_build)
	                }
	                ant {
	                    target('validate')
	                    buildFile(fileBuild)
	                    antInstallation(ant_home)
	                }
	                publishers {
	                    pmd('**/pmd-*.xml') {
	                        healthLimits(0, 1)
	                        thresholdLimit('high')
	                        defaultEncoding('UTF-8')
	                        canRunOnFailed(true)
	                        useStableBuildAsReference(true)
	                        useDeltaValues(true)
	                        computeNew(true)
	                        shouldDetectModules(true)
	                        thresholds(
	                            unstableTotal: [all: 0, high: 0, normal: 0, low: 0],
	                            failedTotal: [all: 0, high: 0, normal: 0, low: 0],
	                            unstableNew: [all: 0, high: 0, normal: 0, low: 0],
	                            failedNew: [all: 0, high: 0, normal: 0, low: 0]
	                        )
	                    }
	                }
	            }
			 }

}





}