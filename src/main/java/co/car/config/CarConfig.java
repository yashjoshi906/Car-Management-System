package co.car.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import net.datafaker.Faker;

@Configuration
@EnableWebMvc
public class CarConfig {

	@Bean
	public Faker faker() {
		return new Faker();
	}

}