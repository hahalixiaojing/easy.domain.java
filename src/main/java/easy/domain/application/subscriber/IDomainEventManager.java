package easy.domain.application.subscriber;

import java.util.List;

import easy.domain.event.IDomainEvent;
import easy.domain.event.ISubscriber;

public interface IDomainEventManager {
	void registerDomainEvent(List<Class<?>> domainEventTypes) throws Exception;
	void registerSubscriber(List<ISubscriber> items) throws Exception;
	<T extends IDomainEvent> void publishEvent(T obj) throws Exception;
}
