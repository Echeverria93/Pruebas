// importando clases de la carpeta utilities

import utilities.GIT_JOB
import utilities.BUILD_JOB

import hudson.model.*
import java.io.File;
import jenkins.model.Jenkins;


// Variables 
String Name_Proyect = "${NAME_PROYECT}"
String Url_Git = "${URL_GIT}"
def project_description = "${PROJECT_DESCRIPTION}"
String Credential_SCM = "SVN_User"
String branch_scm = "${BRANCH_SCM}"
def Patch_Workspace = "${PATCH_WORKSPACE_JENKINS}"
def jdk_x = "${TYPE_JDK}"
String Project_Version = "${PROJECT_VERSION}"
String deploy_stage = "${DEPLOY_STAGE}"
String propertiesFile = "${PROPERTIES_FILE}"
String fileBuild = "${FILE_BUILD}"
String ant_home = "${ANT}"



print "Jdk ***** :"+num


// Listas
def Ambientes = ["Beta","Desarrollo"]



folder('Latam' + '/' + Name_Proyect) {
    description('Ambientes ' + Name_Proyect)
}


for (String item: Ambientes) {

    folder('Latam' + '/' + Name_Proyect + '/' + item) {
        description('Ambiente '+item)
    }

    // JOB GIT 
    def GIT = job('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_GIT') {}
    GIT_JOB.addGIT(GIT, project_description, Credential_SCM, Url_Git, branch_scm)
	
//--------------------------------------------------------------------------------------------------------------------------------------

	    // JOB BUILD 
    def BUILD = job('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_BUILD') {
        customWorkspace(Patch_Workspace + 'Latam' + '/' + Name_Proyect + '/' + 'item' + '/' + Name_Proyect + '_GIT')
		logRotator(1, 5, 1, 5)
        triggers {
            upstream('Latam' + '/' + Name_Proyect + '/' + item + '/' + Name_Proyect + '_GIT', 'SUCCESS')
        }
    }
    BUILD_JOB.addBUILD_WEB_JOB(BUILD, jdk_x, propertiesFile, Name_Proyect, Project_Version, deploy_stage, fileBuild, ant_home  )
	
//---------------------------------------------------------------------------------------------------------------------------------------
	
} // Fin ciclo for


	





