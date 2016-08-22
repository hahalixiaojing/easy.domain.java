package springtest;

import org.springframework.beans.factory.annotation.Autowired;

import easy.domain.application.BaseApplication;

public class Application extends BaseApplication {
	@Autowired
	private User user;
	
	public void add(){
		System.out.println(user.getId());
	}
	
}
