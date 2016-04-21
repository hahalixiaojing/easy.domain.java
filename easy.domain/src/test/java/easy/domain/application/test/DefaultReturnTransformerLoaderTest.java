package easy.domain.application.test;

import org.junit.Test;

import easy.domain.application.DefaultReturnTransformerLoader;

public class DefaultReturnTransformerLoaderTest {
	@Test
	public void loadTest() {
		DefaultReturnTransformerLoader loader = new DefaultReturnTransformerLoader();
		
		loader.find(new DemoApplication());
	}
}
