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
	

static void addPMD_WEB(def job, def jdk_x,String Name_Proyect,String Project_Version, String deploy_stage, String fileBuild, String ant_home, String propertiesFile, String correoJP){

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


static void addPMD_ROBOT(def job, def jdk_x,String Name_Proyect,String Project_Version, String deploy_stage, String fileBuild, String ant_home, String propertiesFile,String item){

def patch_workspace = "${PATCH_WORKSPACE_JENKINS}"
def git_beta = patch_workspace + 'Latam' + '/' + Name_Proyect + '/' + item
 def shell_PMD_RobotB= '''\
 echo "Ejecutando  PMD 5.0"
 cd /var/jenkins_home/resources/REV_COD/pmd-5.0
 sh pmdCheck.sh "1" "'''+git_beta+'''/'''+Name_Proyect+'''_GIT" "'''+git_beta+'''" "'''+Name_Proyect+'''_GIT"
 '''.stripIndent()
 
  def PMD_OK='''\

	Estimado,
		Resultado PMD del proyecto '''+Name_Proyect+'''.
	Atte. Equipo de calidad ZENTA.
	Saludos!'''.stripIndent()

			 job.with{
			 jdk(jdk_x)

  steps {
              shell(shell_PMD_RobotB)
          }

          publishers {
              extendedEmail {
                  recipientList('')
                  defaultSubject('')
                  defaultContent('')
                  attachmentPatterns('1_' + Name_Proyect + '_GIT-pmd.html')
                  contentType('text/html')
                  triggers {
				  
				  if (item =="Beta") {
					    correo ="calidad@zentagroup.com"
					} else {
					    correo = correoJP
					}
                      success {
                          subject('[Jenkins] PMD 5.0 ' + Name_Proyect)
                          content(PMD_OK)
                          recipientList(correo)
                          attachmentPatterns('1_' + Name_Proyect + '_GIT-pmd.html')
                      }

                  }
              }
          }
			 }

}


}