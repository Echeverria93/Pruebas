// importando clases de la carpeta utilities

import utilities.GIT_JOB
import utilities.BUILD_JOB
import utilities.SONARQUBE_JOB
import utilities.PMD_JOB
import utilities.SQLFULL_JOB

import hudson.model.*
import java.io.File;
import jenkins.model.Jenkins;


// Variables 
String Name_Proyect = "${NAME_PROYECT}".trim()
String Url_Git = "${URL_GIT}"
def project_description = "${PROJECT_DESCRIPTION}"
String Credential_SCM = "SVN_User"
String branch_scm = "${BRANCH_SCM}"
def Patch_Workspace = "${PATCH_WORKSPACE_JENKINS}"
def jdk_x = "${TYPE_JDK}"
def tp ="${TYPE_PROJECT}"
String Project_Version = "${PROJECT_VERSION}"
String deploy_stage = "${DEPLOY_STAGE}"
String propertiesFile = "${PROPERTIES_FILE}"
String fileBuild = "${FILE_BUILD}"
String ant_home = "${ANT}"
String correoJP="${EMAIL_RESPONSABLE}"

String user="${USER_DB}".trim()
String pass="${	KEY_DB}".trim()
String host="${HOST_DB}".trim()
String puerto="${PORT_DB}".trim()
String bd="${SSID_DB}".trim()
String directorio="${ROBOT_DIRECTORY}".trim()
String intranet="${INTRANET_NAME}"

// Listas
def Ambientes = ["Beta","Desarrollo"]


folder('Latam' + '/' + Name_Proyect) {
    description('Ambientes ' + Name_Proyect)
}


//--------------------------------------------------------------------------------------------------------------------------------------------


	switch (tp) {

    case "1":
        println("Proyecto Web")
        for (String item: Ambientes) {

            folder('Latam' + '/' + Name_Proyect + '/' + item) {
                description('Ambiente ' + item)
            }


            //PIPELINE 
            buildPipelineView('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_PipeLine') {
                selectedJob('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_GIT')
            }

            // JOB GIT 
            def GIT = job('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_GIT') {}
            GIT_JOB.addGIT(GIT, project_description, Credential_SCM, Url_Git, branch_scm)

            //--------------------------------------------------------------------------------------------------------------------------------------

            // JOB BUILD 
            def BUILD = job('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_BUILD') {
                customWorkspace(Patch_Workspace + 'Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_GIT')
                logRotator(1, 5, 1, 5)
                triggers {
                    upstream('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_GIT', 'SUCCESS')
                }
            }
            BUILD_JOB.addBUILD_WEB_JOB(BUILD, jdk_x, propertiesFile, Name_Proyect, Project_Version, deploy_stage, fileBuild, ant_home)

            //---------------------------------------------------------------------------------------------------------------------------------------

            // JOB SONARQUBE 
            def SONARQUBE = job('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_SONARQUBE') {
                customWorkspace(Patch_Workspace + 'Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_GIT')
                logRotator(1, 5, 1, 5)
                triggers {
                    upstream('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_BUILD', 'SUCCESS')
                }
            }
            SONARQUBE_JOB.addSONARQUBE_WEB_JOB(SONARQUBE, Name_Proyect, Project_Version)

            //---------------------------------------------------------------------------------------------------------------------------------------

            // JOB PMD 
            def PMD = job('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_PMD') {
                customWorkspace(Patch_Workspace + 'Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_GIT')
                logRotator(1, 5, 1, 5)
                triggers {
                    upstream('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_BUILD', 'SUCCESS')
                }
            }
            PMD_JOB.addPMD_WEB_JOB(PMD, jdk_x, Name_Proyect, Project_Version, deploy_stage, fileBuild, ant_home, propertiesFile)

            //---------------------------------------------------------------------------------------------------------------------------------------

            // JOB SQLFULL 
            def SQLFULL = job('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_SQLFULL') {
                customWorkspace(Patch_Workspace + 'Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_GIT')
                logRotator(1, 5, 1, 5)
                triggers {
                    upstream('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_SONARQUBE', 'SUCCESS')
                }
            }
            SQLFULL_JOB.addSQLFULL_WEB_JOB(SQLFULL, Name_Proyect, Project_Version, item, tp, user, pass, host, puerto, bd, directorio, intranet, correoJP)



        } // Fin ciclo for

        break


    case "2":
        //--------------------------------------------------------------------------------------------

        println("Robot")
        break

    default:
        println("Valor no Encontrado")
        break
}










	





