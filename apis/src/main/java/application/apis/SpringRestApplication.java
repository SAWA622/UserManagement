package application.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;



@SpringBootApplication
@EnableSpringDataWebSupport
public class SpringRestApplication {
	public static void main(String[] args) {
		System.out.println("1");
		  SpringApplication.run(SpringRestApplication.class, args); 
	}
}
