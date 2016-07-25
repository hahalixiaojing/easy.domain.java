package easy.domain.application.test;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class JarFilePathTest {

	@Test
	public void subStringJarFileTest() {
		String path = "file:/F:/springdemo/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/com.demo.web/WEB-INF/lib/com.demo.application-1.0.0.jar!/com/demo/application/user/adduserdomainevents/";

		String[] lines = path.split("!");

		String line0 = lines[0];

		System.out.println(line0);

		String line = StringUtils.stripStart(line0, "file:/");

		System.out.println(line);

		File f = new File(line);

		Assert.assertTrue(f.exists());
	}

}
