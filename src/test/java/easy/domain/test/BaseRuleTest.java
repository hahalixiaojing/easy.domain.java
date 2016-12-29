package easy.domain.test;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.*;

import easy.domain.rules.BaseRule;

public class BaseRuleTest {
	/**
	 * 属性测试
	 */
	@Test
	public void stringPropertyValueTest() {
		UserRule<String> stringUserRule = new UserRule<String>("name",
				"testName");

		boolean result = stringUserRule.isSatisfy(new User());
		Assert.assertTrue(result);
	}

	/**
	 * 数字属性测试
	 */
	@Test
	public void intPropertyValueTest() {
		UserRule<Integer> intUserRule = new UserRule<Integer>("age", 1);

		boolean result = intUserRule.isSatisfy(new User());
		Assert.assertTrue(result);
	}

	/**
	 * boolean属性测试
	 */
	@Test
	public void booleanPropertyValueTest() {
		UserRule<Boolean> intUserRule = new UserRule<Boolean>("isMan", true);

		boolean result = intUserRule.isSatisfy(new User());
		Assert.assertTrue(result);
	}
	/**
	 * 日期属性测试
	 */
	@Test
	public void datePropertyValueTest(){
		Calendar c = Calendar.getInstance();
		c.set(2015, 6, 22,0,0,0);
		Date date = c.getTime();

		DateTimeUserRule intUserRule = new DateTimeUserRule("create", date);

		boolean result = intUserRule.isSatisfy(new User());
		Assert.assertTrue(result);
	}
	/**
	 * 复杂类型属性测试
	 */
	@Test
	public void complexPropertyValueTest(){
		UserRule<String> userRule =new UserRule<String>("city.name", "lixiaojing");
		
		boolean result = userRule.isSatisfy(new User());
		
		Assert.assertTrue(result);
	}
	
	/**
	 * 私有属性测试
	 */
	@Test
	public void privatePropertyValueTest(){
		UserRule<Integer> rule =new UserRule<Integer>("head", 100);
		
		boolean result = rule.isSatisfy(new User());
		Assert.assertTrue(result);
	}
	/**
	 * 父类属性测试
	 */
	@Test
	public void parentPropertyValueTest(){
		UserRule<Integer> rule =new UserRule<Integer>("id", 1000);
		
		boolean result = rule.isSatisfy(new User());
		Assert.assertTrue(result);
	}

}

class DateTimeUserRule extends BaseRule<User, Date>{

	private Date date;
	public DateTimeUserRule(String property,Date date) {
		super(property);
		this.date=DateUtils.truncate(date, Calendar.SECOND);
	}
	@Override
	public boolean isSatisfy(User model) {
		Date v = this.getObjectAttrValue(model);
		
		v = DateUtils.truncate(v, Calendar.SECOND);
		
		return this.date.compareTo(v) == 0;
	}
	
}

class UserRule<Value> extends BaseRule<User, Value> {

	private Value value;

	public UserRule(String property, Value value) {
		super(property);
		this.value = value;
	}

	@Override
	public boolean isSatisfy(User model) {
		Value v = this.getObjectAttrValue(model);
		return v.equals(value);
	}
}
