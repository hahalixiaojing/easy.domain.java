package easy.domain.application.test;

import org.junit.Test;

import easy.domain.application.*;

public class ApplicationFactoryTest {
	@Test
	public void loadTest() throws Exception {

		ApplicationFactory.instance().register(new DemoApplication());

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
}
