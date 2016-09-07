package easy.domain.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import easy.domain.event.DomainEventPublisher;
import easy.domain.event.IDomainEvent;
import easy.domain.event.IDomainEventSubscriber;
import easy.domain.event.ISubscriber;

public class TaskDomainEventManager implements IDomainEventManager {

	private final HashMap<String, List<ISubscriber>> DOMAIN_EVENTS = new HashMap<>();

	private List<ISubscriber> domainEvents(String name) {
		return ObjectUtils.defaultIfNull(this.DOMAIN_EVENTS.get(name),
				new ArrayList<ISubscriber>(0));
	}

	@Override
	public void registerDomainEvent(List<Class<?>> domainEventTypes) {
	}

	@Override
	public void registerSubscriber(String name, List<ISubscriber> item) {
		this.DOMAIN_EVENTS.put(name, item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IDomainEvent> void publishEvent(String name, T obj) {

		System.out.println("xxx=" + name);

		List<ISubscriber> subscribers = this.domainEvents(name);
		System.out.println("subscribers size = " + subscribers.size());
		DomainEventPublisher publisher = new DomainEventPublisher();
		for (ISubscriber sub : subscribers) {
			publisher.subscribe((IDomainEventSubscriber<T>) sub);
		}

		publisher.publish(obj);
	}
}
