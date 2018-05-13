package utilities

import javaposse.jobdsl.dsl.Context;


public class SQLFULL_JOB implements Context {

private String Name_Proyect
private String Project_Version
private String item
private def tp

private String user
private String pass
private String host
private String puerto
private String bd
private String directorio
private String intranet
private String correoJP


static void addSQLFULL_WEB_JOB(def job, String Name_Proyect, String Project_Version, String item, def tp, String user, String pass, String host, String puerto, String bd, String directorio, String intranet, String correoJP){
String correo

def shell_sql_full='''\
echo "Ejecutando  ApiSQL_Scanner.jar"
cd /var/jenkins_home/resources/ApiSQL_Scanner
java -jar ApiSQL_Scanner.jar "'''+Name_Proyect+'''-'''+Project_Version+'''" "'''+Name_Proyect+'''/'''+item+'''/'''+Name_Proyect+'''_GIT" "'''+tp+'''" "'''+user+'''" "'''+pass+'''" "'''+host+'''" "'''+puerto+'''" "'''+bd+'''" "'''+directorio+'''" "'''+intranet+'''"
'''.stripIndent()

def sql_full_error='''\
	Estimado,
		Se ha detectado full table scan en el proyecto '''+Name_Proyect+'''-'''+Project_Version+'''
	Atte. Equipo de calidad ZENTA.
	Saludos! '''.stripIndent()
	
def sql_full_OK='''\

	Estimado,
		El análisis de las consultas SQL del proyecto '''+Name_Proyect+'''-'''+Project_Version+''', no ha detectado querys con SQL full.
	Atte. Equipo de calidad ZENTA.
	Saludos!'''.stripIndent()

			 job.with{
			 jdk('JAVA_HOME')
			 
			             steps {
			                 shell(shell_sql_full)
			             }

            publishers {
                extendedEmail {
                    recipientList('')
                    defaultSubject('')
                    defaultContent('')
                    attachmentPatterns('Process_Query_' + Name_Proyect + '-' + Project_Version + '.pdf')
                    contentType('text/html')
                    triggers {
					
					if (item =="Beta") {
					    correo ="calidad@zentagroup.com"
					} else {
					    correo = correoJP
					}
                        success {
                            subject('[Jenkins] SQL Full_Scanner-' + Name_Proyect + '-' + Project_Version)
                            content(sql_full_OK)
                            recipientList(correo)
                            attachmentPatterns('Report_' + Name_Proyect + '-' + Project_Version + '.pdf, Report_not_process_' + Name_Proyect + '-' + Project_Version + '.pdf')
                        }
                        failure {
                            subject('[Jenkins] SQL Full_Scanner-' + Name_Proyect + '-' + Project_Version)
                            content(sql_full_error)
                            recipientList(correo)
                            attachmentPatterns('Report_not_process_' + Name_Proyect + '-' + Project_Version + '.pdf')
                        }
                    }
                }
                wsCleanup()
            }


			 }

}


}