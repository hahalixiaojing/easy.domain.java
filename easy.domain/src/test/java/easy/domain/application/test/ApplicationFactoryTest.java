package easy.domain.application.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import easy.domain.application.*;
import easy.domain.application.test.demo.DemoApplication;

public class ApplicationFactoryTest {

	@BeforeClass
	public static void setUp() throws Exception {
		ApplicationFactory.instance().register(new DemoApplication());
	}

	@Test
	public void loadTest() throws Exception {

		DemoApplication demo = ApplicationFactory.instance().get(
				DemoApplication.class);

		BaseReturn<String> ret = demo.add();
		String result1 = ret.resultDefault();
		System.out.println(result1);

		int len = ret.result(new IDefaultReturnTansformer<String,Integer>() {

			@Override
			public Integer getValue(String t) {
				return t.length();
			}
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
