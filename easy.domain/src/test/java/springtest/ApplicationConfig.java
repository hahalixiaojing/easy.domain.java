package springtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import easy.domain.application.ApplicationBuild;

@Configuration
public class ApplicationConfig {

	private final ApplicationBuild build = new ApplicationBuild();

	@Bean(name="user1")
	public User getUser() {
		return new User(100);
	}

	@Bean(name = "user2")
	public User getUser2() {
		return new User(500);
	}

	@Bean
	public Application getApplication() {
		return build.build(new Application());
	}

	@Bean
	public Application2 getApplication2() {
		return build.build(new Application2());
	}
}
