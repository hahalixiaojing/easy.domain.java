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
		String result1 = ret.resultDefault();
		System.out.println(result1);

		int len = ret.resultToConvert((s) -> {

			return s.length();
		});

		System.out.println(len);

		System.out.print(Thread.currentThread().getId());

		BaseReturn<String> ret2 = demo.save();
		System.out.println(ret2.resultDefault());
	}

	@Test
	public void defaultValueTest() throws Exception {
		String defaultValue = ApplicationFactory.instance()
				.get(DemoApplication.class).defaultValueTest().resultDefault();

		Assert.assertEquals("default value", defaultValue);

	}
}
