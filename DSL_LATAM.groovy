// importando clases de la carpeta utilities
import utilities.GIT_JOB
import utilities.BUILD_JOB

import hudson.model.*
import java.io.File;
import java.util.ArrayList
import jenkins.model.Jenkins;


// Variables 
String Name_Proyect = "${NAME_PROYECT}"
String Url_Git = "${URL_GIT}"
def project_description = "${PROJECT_DESCRIPTION}"
String Credential_SCM = "SVN_User"
String branch_scm = "${BRANCH_SCM}"
def patch_workspace = "${PATCH_WORKSPACE_JENKINS}"
String  jdk_x = "${JDK}"

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
	
	// JOB BUILD
	def Build = job('Latam' + '/' + project_name + '/' + item + '/' + project_name + '_BUILD') {
	  customWorkspace(patch_workspace + 'Latam' + '/' + project_name + '/' + item + '/' + project_name + '_GIT')
	}
	GIT_JOB.addBUILD_WEB_JOB(Build, Description_proyect, Credential_SCM, Url_Git, Environments)

}





