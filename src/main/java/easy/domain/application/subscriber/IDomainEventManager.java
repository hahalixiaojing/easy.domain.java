package easy.domain.application.subscriber;

import java.util.Set;

import easy.domain.event.IDomainEvent;

public interface IDomainEventManager {

    void registerDomainEvent(Class<?> domainEventType);

    void registerSubscriber(ISubscriber subscriber);

    void registerSubscriber(ISubscriber subscriber, String alias);

    void registerDomainEvent(Set<Class<?>> domainEventTypes);

    void registerSubscriber(Set<ISubscriber> items);

    <T extends IDomainEvent> void publishEvent(T obj);

    <T extends IDomainEvent> void publishEvent(T obj, String subscriber);
}
