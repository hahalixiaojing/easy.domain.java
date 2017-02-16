package easy.domain.application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import easy.domain.application.subscriber.support.DefaultDomainEventLoader;
import easy.domain.application.subscriber.support.DefaultDomainEventSubscriberLoader;
import easy.domain.application.subscriber.IDomainEventLoader;
import easy.domain.application.subscriber.IDomainEventSubscriberLoader;
import easy.domain.event.ISubscriber;

public class ApplicationBuilder {
	private IDomainEventSubscriberLoader domainEventSubscriberLoader;
	private IDomainEventLoader domainEventLoader;

	public ApplicationBuilder() {
		this.domainEventSubscriberLoader = new DefaultDomainEventSubscriberLoader();
		this.domainEventLoader = new DefaultDomainEventLoader();
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseApplication> T build(T application) throws Exception {
		BaseApplication baseApplication = (BaseApplication) application;


		HashMap<String, List<ISubscriber>> subscribers = domainEventSubscriberLoader
				.find(baseApplication);
		for (Entry<String, List<ISubscriber>> entry : subscribers.entrySet()) {
			baseApplication.registerSubscriber(new HashSet<>(entry.getValue()));
		}

		List<Class<?>> domainEvents = this.domainEventLoader
				.load(baseApplication);
		baseApplication.registerDomainEvent(new HashSet<>(domainEvents));

		return (T) baseApplication;
	}
}
