package springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import easy.domain.application.BaseApplication;

public class Application extends BaseApplication  {
	@Autowired
	@Qualifier("user1")
	private User user;
	
	@Autowired
	public ApplicationContext context;
	

	public void add() {
		System.out.println(user.getId() + user.toString());

 		User user = (User) context.getBean("user2");
 		
 		
 		
 		System.out.println(user.getId());
	}

	
	
}
