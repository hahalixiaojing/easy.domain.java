package easy.domain.application.test;

import java.util.HashMap;
import java.util.List;

import org.junit.*;

import org.junit.Test;

import easy.domain.application.DefaultReturnTransformerLoader;
import easy.domain.application.IReturnTransformer;
import easy.domain.application.test.demo.DemoApplication;

public class DefaultReturnTransformerLoaderTest {
	@Test
	public void loadTest() {
		DefaultReturnTransformerLoader loader = new DefaultReturnTransformerLoader();

		HashMap<String, List<IReturnTransformer>> list = loader
				.find(new DemoApplication());

		Assert.assertTrue(list.size() > 0);
	}
}
