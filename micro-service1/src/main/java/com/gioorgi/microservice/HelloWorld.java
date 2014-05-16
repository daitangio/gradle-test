package com.gioorgi.microservice;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springsource.loaded.agent.SpringLoadedPreProcessor;

import com.gioorgi.microservice.audit.AuditServiceController;

/**
 * Main entry point
 * @author jj
 *
 */
@Controller
@EnableAutoConfiguration()
@ComponentScan
@EnableBatchProcessing
public class HelloWorld {

	/*** Demo job
	 */
	
	@Autowired(required=false)
	MicroServiceConfiguration config;
	
	/** For building jobs see
	 * http://docs.spring.io/spring-boot/docs/1.0.2.RELEASE/reference/html/howto-batch-applications.html#howto-execute-spring-batch-jobs-on-startup
	 * and @EnableBatchProcessing in
	 * http://docs.spring.io/spring-batch/2.2.x/apidocs/index.html
	 */
	@Autowired
    private JobBuilderFactory jobs;

	
//	public Job myjob() {
//		
//	}
	
	@Autowired(required=false)
	AuditServiceController auditServiceController;
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Value("${app.version:UNKNOWN}")
	String version;
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

	@RequestMapping("/about")
    @ResponseBody
    String about() {
        return "The Gradle+SpringBoot+SpringLoaded_2014 JJ's_Demo_is here. Demo_Version:"+version;
    }

	@RequestMapping("/version")
    @ResponseBody
    String plainTextVersionForFriends() {
        return ""+version;
    }
	
	@Autowired
	ReloadMonitor reloadMonitor;
	
	@PostConstruct
	public void registerReloadMoniotrOnce() {
		SpringLoadedPreProcessor.registerGlobalPlugin(reloadMonitor);
		logger.info("** Reload Monitor registered **");
	}	
	
	/**
	 * Is called by the reload monitor if 
	 * isRefreshSystemOnReload is true.
	 * Used to foce a fast system refresh in developing mode
	 */
	@PostConstruct
	public void sayHelloAndBeNice() {
		logger.info("\n\n\t  "+about().replace(" ", "\n\t")+"\n\n");		
		if(auditServiceController!=null) {
			logger.info("Audit Instance="+auditServiceController);
		} else {
			logger.info("No audit controller");
		}
		logger.info("Job Factory="+jobs);
		if(config.isAutodiscovery()) {
			logger.info("mc.autodiscovery=true Auto discovery enabled");
		}
		if(config.isRefreshSystemOnReload()) {
			logger.info("mc.refreshSystemOnReload=true");
			logger.warn("** Refresh on reload is not a production feature");
		}
		logger.info("\n\n\n");
	}
	
    public void setVersion(String version) {
		this.version = version;
	}

	public static void main(String[] args) throws Exception {   
//		ApplicationListener<ApplicationPreparedEvent> sayHello = new ApplicationListener<ApplicationPreparedEvent>() {
//
//			@Override
//			public void onApplicationEvent(ApplicationPreparedEvent event) {
//				System.err.println("\n\n\t DEMO READY\n\n");				
//			}
//		};
		SpringApplicationBuilder ab=new SpringApplicationBuilder(HelloWorld.class);
//		ab.application().addListeners(sayHello);
		
		
		
    	ab.run(args);
    
    }
}
