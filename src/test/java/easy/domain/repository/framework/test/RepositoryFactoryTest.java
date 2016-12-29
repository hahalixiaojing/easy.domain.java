package easy.domain.repository.framework.test;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.SystemUtils;
import org.junit.*;

import easy.domain.repository.framework.IRepositoryFactory;
import easy.domain.repository.framework.RepositoryFactoryBuilder;


public class RepositoryFactoryTest {

	@Test
	public void createRepositoryFactoryTest() throws FileNotFoundException{
		
		Path p = Paths.get(SystemUtils.getUserDir().getAbsolutePath(), "repositories.xml" ) ;
		
		File file = p.toFile();
		
		RepositoryFactoryBuilder builder =new RepositoryFactoryBuilder();
		IRepositoryFactory factory = builder.build(new FileInputStream(file));
		
		
		
		IDemoRepository demoRep = factory.get(IDemoRepository.class);
		
		String name = demoRep.findBy(0).getName();
		
		Assert.assertEquals("lixiaojing", name);
		Assert.assertNotNull(demoRep);
		
		
//		DemoRepository r =new DemoRepository();
//		
//		DemoRepositoryProxy proxy =new DemoRepositoryProxy();
//		IDemoRepository rxy =(IDemoRepository) proxy.bind(r);
//		
//		boolean result = IDao.class.isAssignableFrom(rxy.getClass());
//		Assert.assertTrue(result);
//		
//		result = IRepository.class.isAssignableFrom(rxy.getClass());
//		Assert.assertTrue(result);
//		
//		rxy.add();
		
	}
}
