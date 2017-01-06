package easy.domain.application.subscriber;

import java.util.List;

import easy.domain.event.IDomainEvent;
import easy.domain.event.ISubscriber;

public interface IDomainEventManager {
	void registerDomainEvent(List<Class<?>> domainEventTypes);
	void registerSubscriber(List<ISubscriber> items);
	<T extends IDomainEvent> void publishEvent(T obj) throws Exception;
}
