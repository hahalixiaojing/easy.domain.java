package easy.domain.application.test;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import easy.domain.application.DefaultDomainEventSubscriberLoader;
import easy.domain.event.ISubscriber;

public class DefaultDomainEventSubscriberLoaderTest {
	@Test
	public void loadTest() {
		DefaultDomainEventSubscriberLoader loader = new DefaultDomainEventSubscriberLoader();
		
		HashMap<String, List<ISubscriber>> maps = loader.find(new DemoApplication());
		
		System.out.println(maps.size());
		
		Assert.assertTrue(maps.size() == 6);
	}
}
