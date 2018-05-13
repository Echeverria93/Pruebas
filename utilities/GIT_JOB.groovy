package utilities

import javaposse.jobdsl.dsl.Context;

public class GIT_JOB implements Context {
    private String Url_Git 					
    private String Credential_SCM				
    private String branch_scm	
	
    static void addGIT(def job, String Description_proyect, String Credential_SCM, String Url_Git, String branch_scm) {
        job.with {
		logRotator(1,5,1,5)	
		description(Description_proyect)
		scm{  
			git{
			  	remote{
			    	url(Url_Git )
		            branch(branch_scm)
		            credentials(Credential_SCM)
		          }
		        }
		}
        }
    }
}