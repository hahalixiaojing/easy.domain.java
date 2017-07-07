package easy.domain.application.subscriber;

import java.util.Set;

import easy.domain.event.IDomainEvent;

public interface IDomainEventManager {

    void registerDomainEvent(Class<?> domainEventType);

    void registerSubscriber(ISubscriber subscriber);

    void registerSubscriber(ISubscriber subscriber, String alias);

    void registerDomainEvent(Set<Class<?>> domainEventTypes) throws Exception;

    void registerSubscriber(Set<ISubscriber> items) throws Exception;

    <T extends IDomainEvent> void publishEvent(T obj) throws Exception;

    <T extends IDomainEvent> void publishEvent(T obj, String subscriber) throws Exception;
}
