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

		BaseReturn<String> ret = demo.add();
		Object obj = ret.result(new ReturnContext());

		String result1 = ret.resultDefault();
		System.out.println(result1);

		int len = ret.resultToConvert((s) -> {

			return s.length();
		});

		System.out.println(len);

		System.out.print(Thread.currentThread().getId());
		System.out.println(obj);

		BaseReturn<String> ret2 = demo.save();
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
		String defaultValue = ApplicationFactory.instance()
				.get(DemoApplication.class).defaultValueTest().resultDefault();

		Assert.assertEquals("default value", defaultValue);

	}
}
