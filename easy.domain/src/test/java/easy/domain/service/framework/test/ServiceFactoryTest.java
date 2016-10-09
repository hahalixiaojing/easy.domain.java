package easy.domain.service.framework.test;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import easy.domain.service.framework.IServiceFactory;
import easy.domain.service.framework.ServiceFactoryBuilder;

public class ServiceFactoryTest {
	@Test
	public void loadXmlTest(){
		
		InputStream stream = this.getClass().getResourceAsStream("/easy/domain/service/framework/test/service.xml");
//		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("easy/domain/service/framework/test/service.xml");
		Assert.assertNotNull(stream);
		
		ServiceFactoryBuilder builder =new ServiceFactoryBuilder();
		IServiceFactory factory = builder.build(stream);
		
		Assert.assertNotNull(factory);
			
		IDemoService service = factory.get(IDemoService.class);
		Assert.assertNotNull(service);
		
		int result = service.GetByUserId(10);
		Assert.assertEquals(10, result);
		
	}
}
