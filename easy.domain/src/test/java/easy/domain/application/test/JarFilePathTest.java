package easy.domain.application.test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class JarFilePathTest {

	@Test
	public void subStringJarFileTest() {
		String path = "file:/F:/springdemo/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/com.demo.web/WEB-INF/lib/com.demo.application-1.0.0.jar!/com/demo/application/user/adduserdomainevents/";

		try {
			URI uri =new URI(path);
			
			String p = uri.getPath();
			System.out.println(p);
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] lines = path.split("!");

		String line0 = lines[0];

		System.out.println(line0);

		String line = StringUtils.stripStart(line0, "file:/");

		System.out.println(line);

		File f = new File(line);

		Assert.assertTrue(f.exists());
	}

}
