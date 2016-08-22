package springtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
	@Bean
	public User getUser(){
		return new User(100);
	}
	@Bean
	public Application getApplication(){
		return new Application();
	}
}
