package me.woemler.gdw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author woemler
 */
@Configuration
@ComponentScan(basePackages = { "me.woemler.gdw" })
@SpringBootApplication
public class Application  {

	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}
	
}
