package easy.domain.application;

import java.util.List;

import easy.domain.event.IDomainEvent;
import easy.domain.event.ISubscriber;

public interface IDomainEventManager {
	void registerDomainEvent(List<Class<?>> domainEventTypes);

	void registerSubscriber(String name, List<ISubscriber> items);

	<T extends IDomainEvent> void publishEvent(String name, T obj);
}
