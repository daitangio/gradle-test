package com.gioorgi.microservice;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 *  Happily run with
 * java -jar build/libs/micro-service1.jar
 * Look at
 *  http://localhost:8080/configprops
 * for configured properties 
 * # --debug  --spring.config.location=src/main/java/config/
 * @author jj
 *
 */
@Controller
@EnableAutoConfiguration()
@ComponentScan
public class HelloWorld {

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
        return "The Gradle+SpringBoot+SpringLoaded 2014 JJ's Demo is here. Demo Version:"+version;
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
