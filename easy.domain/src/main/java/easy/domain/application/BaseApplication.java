package easy.domain.application;

import java.util.HashMap;
import java.util.List;

import easy.domain.event.DomainEventPublisher;
import easy.domain.event.IDomainEvent;
import easy.domain.event.IDomainEventSubscriber;
import easy.domain.event.ISubscriber;

public class BaseApplication implements IApplication {

	private final HashMap<String, List<IReturnTransformer>> TRANSFORMER = new HashMap<String, List<IReturnTransformer>>();

	private final HashMap<String, List<ISubscriber>> DOMAINEVENTS = new HashMap<String, List<ISubscriber>>();

	public BaseApplication() {

	}

	void registerReturnTransformer(String name, List<IReturnTransformer> transformer) {
		this.TRANSFORMER.put(name, transformer);
	}

	void registerDomainEvent(String name, List<ISubscriber> item) {
		this.DOMAINEVENTS.put(name, item);
	}

	private List<IReturnTransformer> getTransformer(String name) {
		return this.TRANSFORMER.get(name);
	}

	private List<ISubscriber> getDomainEvents(String name) {

		this.DOMAINEVENTS.values().stream().forEach(s -> System.out.println(s));

		System.out.println(name);
		return this.DOMAINEVENTS.get(name);
	}

	protected <T> IReturn write(String mName, T obj) {
		DefaultReturn<T> ret = new DefaultReturn<T>(obj,
				this.getTransformer(mName));
		return ret;
	}

	protected <T, EventDATA extends IDomainEvent> IReturn writeAndPublishDomainEvent(
			String mName, T obj, EventDATA eventData) {
		try {
			this.publishEvent(mName, eventData);
		} finally {
		}

		return this.write(mName, obj);
	}

	@SuppressWarnings("unchecked")
	protected <T extends IDomainEvent> void publishEvent(String mName, T obj) {
		List<ISubscriber> subscribers = this.getDomainEvents(mName);
		System.out.println("subscribers=" + subscribers);
		DomainEventPublisher publisher = new DomainEventPublisher();

		for (ISubscriber sub : subscribers) {
			publisher.subscribe((IDomainEventSubscriber<T>) sub);
		}

		publisher.publish(obj);
	}

}
