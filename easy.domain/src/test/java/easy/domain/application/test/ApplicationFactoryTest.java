package easy.domain.application.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import easy.domain.application.*;

public class ApplicationFactoryTest {

	@BeforeClass
	public static void setUp() {
		ApplicationFactory.instance().register(new DemoApplication());
	}

	@Test
	public void loadTest() throws Exception {

		DemoApplication demo = ApplicationFactory.instance().get(
				DemoApplication.class);

		IReturn ret = demo.add();
		Object obj = ret.result(new ReturnContext());

		System.out.print(Thread.currentThread().getId());
		System.out.println(obj);

		IReturn ret2 = demo.save();
		Object obj2 = ret2.result(new ReturnContext());
		System.out.print(Thread.currentThread().getId());
		System.out.println(obj2);

		Object obj3 = ret.result(new ReturnContext("", "8"));
		System.out.print(Thread.currentThread().getId());
		System.out.println(obj3);

		System.out.println(" ");
	}

	@Test
	public void defaultValueTest() throws Exception {
		String defaultValue = (String) (ApplicationFactory.instance()
				.get(DemoApplication.class).defaultValueTest().resultDefault());
		
		Assert.assertEquals("default value", defaultValue);

	}
}
