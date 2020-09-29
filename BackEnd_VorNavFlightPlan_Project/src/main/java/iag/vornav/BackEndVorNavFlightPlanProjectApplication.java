package iag.vornav;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 
 * @author Ivan Alonso
 *
 */
@SpringBootApplication
public class BackEndVorNavFlightPlanProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndVorNavFlightPlanProjectApplication.class, args);
	}

	/**
	 * Allow all REST Web API requests from static web project (FrontEnd)
	 * 
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET","POST","PUT","DELETE");
			}
		};
	}	
	
}
