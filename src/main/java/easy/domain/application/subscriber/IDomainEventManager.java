package easy.domain.application.subscriber;

import java.util.Set;

import easy.domain.event.IDomainEvent;
import easy.domain.event.ISubscriber;

public interface IDomainEventManager {
	void registerDomainEvent(Set<Class<?>> domainEventTypes) throws Exception;
	void registerSubscriber(Set<ISubscriber> items) throws Exception;
	<T extends IDomainEvent> void publishEvent(T obj) throws Exception;
}
