package utilities

import javaposse.jobdsl.dsl.Context;


public class PMD_JOB implements Context {

    private String propertiesFile
	private String ant_home
	private String fileBuild

static void addPMD_WEB_JOB(def job,String fileBuild, String ant_home, String propertiesFile){

			 job.with{
			 jdk('JAVA_HOME')

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