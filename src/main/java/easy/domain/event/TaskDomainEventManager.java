package easy.domain.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;

import easy.domain.application.subscriber.IDomainEventManager;

public class TaskDomainEventManager implements IDomainEventManager {

    private final List<ISubscriber> subscribers = new ArrayList<>();
    private final DomainEventPublisher publisher = new DomainEventPublisher();


    @Override
    public void registerDomainEvent(Set<Class<?>> domainEventTypes) {
    }

    @Override
    public void registerSubscriber(Set<ISubscriber> item) {
        this.subscribers.addAll(item);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IDomainEvent> void publishEvent(T obj) throws Exception {
        publisher.publish(obj, subscribers);
    }
}
