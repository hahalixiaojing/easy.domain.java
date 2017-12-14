package easy.domain.application;

import java.util.Set;

import easy.domain.application.result.IBaseResult;
import easy.domain.application.result.support.Result;
import easy.domain.application.subscriber.IDomainEventManager;

import easy.domain.event.IDomainEvent;
import easy.domain.application.subscriber.ISubscriber;
import easy.domain.event.TaskDomainEventManager;

public abstract class BaseApplication implements IApplication {

    private final IDomainEventManager manager;

    public BaseApplication() {
        this.manager = new TaskDomainEventManager();
    }

    public BaseApplication(IDomainEventManager manager) {
        this.manager = manager;
    }


    public void registerDomainEvent(Class<?> domainEventType) {
        this.manager.registerDomainEvent(domainEventType);
    }

    public void registerSubscriber(ISubscriber subscriber, String subscriberName) {
        this.manager.registerSubscriber(subscriber, subscriberName);
    }

    public void registerSubscriber(Set<ISubscriber> item)  {
        this.manager.registerSubscriber(item);
    }


    public void registerDomainEvent(Set<Class<?>> events) {
        this.manager.registerDomainEvent(events);
    }


    protected <T> IBaseResult<T> write(T obj) {
        Result<T> result = new Result<>(obj);
        return result;
    }

    protected <T, EventDATA extends IDomainEvent> IBaseResult<T> writeAndPublishEvent(
            T obj, EventDATA eventData) {
        try {
            this.publishEvent(eventData);

        } catch (Exception e) {

        } finally {
        }

        return this.write(obj);
    }

    protected <T extends IDomainEvent> void publishEvent(T obj)  {
        this.manager.publishEvent(obj);
    }

    protected <T extends IDomainEvent> void publishEvent(T obj, String subscribe)  {
        this.manager.publishEvent(obj, subscribe);

    }
}