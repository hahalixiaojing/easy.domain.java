package easy.domain.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import easy.domain.event.ISubscriber;

public class ApplicationBuild {
	private IReturnTransformerLoader returnTransfomerLoader;
	private IDomainEventSubscriberLoader domainEventSubscriberLoader;
	private IDomainEventLoader domainEventLoader;

	public ApplicationBuild() {
		this.returnTransfomerLoader = new DefaultReturnTransformerLoader();
		this.domainEventSubscriberLoader = new DefaultDomainEventSubscriberLoader();
		this.domainEventLoader = new DefaultDomainEventLoader();
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseApplication> T build(T application) throws Exception {
		BaseApplication baseApplication = (BaseApplication) application;

		HashMap<String, List<IReturnTransformer>> transformers = returnTransfomerLoader
				.find(baseApplication);

		for (Entry<String, List<IReturnTransformer>> entry : transformers
				.entrySet()) {

			baseApplication.registerReturnTransformer(entry.getKey(),
					entry.getValue());
		}

		HashMap<String, List<ISubscriber>> subscribers = domainEventSubscriberLoader
				.find(baseApplication);
		for (Entry<String, List<ISubscriber>> entry : subscribers.entrySet()) {
			baseApplication.registerSubscriber(entry.getValue());
		}

		List<Class<?>> domainEvents = this.domainEventLoader
				.load(baseApplication);
		baseApplication.registerDomainEvent(domainEvents);

		return (T) baseApplication;
	}
}
