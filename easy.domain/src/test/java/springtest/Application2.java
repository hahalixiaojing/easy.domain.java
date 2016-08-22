package springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import easy.domain.application.BaseApplication;

public class Application2 extends BaseApplication {
	@Autowired
	@Qualifier("user1")
	private User user;
	
	public void add(){
		System.out.println(user.getId() + user.toString());
	}
}
