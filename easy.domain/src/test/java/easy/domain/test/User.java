package easy.domain.test;

import java.util.Calendar;
import java.util.Date;

import easy.domain.base.BrokenRuleMessage;

public class User extends BaseUser {

	private int age = 1;
	private String name = "testName";
	private String email = "";

	public User() {

	}

	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	private int getHead() {
		return 100;
	}

	public int getHeads() {
		return this.getHead();
	}

	public boolean getIsMan() {
		return true;
	}

	public Date getCreate() {
		Calendar c = Calendar.getInstance();
		c.set(2015, 6, 22, 0, 0, 0);

		return c.getTime();
	}

	public City getCity() {
		return new City();
	}

	@Override
	protected BrokenRuleMessage getBrokenRuleMessages() {
		return new UserBrokenRuleMessage();
	}

	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		return null;
	}
}
