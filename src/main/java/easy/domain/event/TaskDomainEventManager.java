package easy.domain.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import easy.domain.application.subscriber.IDomainEventManager;

public class TaskDomainEventManager implements IDomainEventManager {

    private final List<ISubscriber> subscribers = new ArrayList<>();
    private final DomainEventPublisher publisher = new DomainEventPublisher();


    @Override
    public void registerDomainEvent(List<Class<?>> domainEventTypes) {
    }

    @Override
    public void registerSubscriber(List<ISubscriber> item) {
        this.subscribers.addAll(item);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IDomainEvent> void publishEvent(T obj) throws Exception {
        publisher.publish(obj, subscribers);
    }
}
