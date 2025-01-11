package sen.saloum.saloum_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "sen.saloum.saloum_service")
public class SaloumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaloumServiceApplication.class, args);
	}

}
