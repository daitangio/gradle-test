package com.gioorgi.microservice;


import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 *  Happily run with
 * java -jar build/libs/micro-service1.jar  --debug  --spring.config.location=src/main/java/config/
 * @author jj
 *
 */
@Controller
@EnableAutoConfiguration()
public class HelloWorld {

	@RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
    	   	
        SpringApplication.run(HelloWorld.class, args);
    }
}
