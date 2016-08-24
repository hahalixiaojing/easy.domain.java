package springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import easy.domain.application.BaseApplication;

public class Application2 extends BaseApplication {
	@Autowired
	@Qualifier("user1")
	private User user;
	@Autowired
	private TestService serivce;
	@Autowired
	@Qualifier("a1")
	private MyDataSource s;
	
	@Autowired
	@Qualifier("a2")
	private MyDataSource s2;
	
	public void add(){
		System.out.println(user.getId() + user.toString());
		this.serivce.add();
		
		
		System.out.println(s.getA());
		System.out.println(s2.getA());
	}
}
