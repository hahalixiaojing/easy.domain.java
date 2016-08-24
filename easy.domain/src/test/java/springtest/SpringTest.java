package springtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class, Datasource.class })
public class SpringTest {

	@Autowired
	private Application application;

	@Autowired
	Application2 application2;

	@Test
	public void test() {
		application.add();
		application2.add();
	}
}
