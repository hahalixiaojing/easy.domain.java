package easy.domain.application;

import java.util.List;

import easy.domain.application.result.IBaseResult;
import easy.domain.application.result.support.Result;
import easy.domain.application.subscriber.IDomainEventManager;

import easy.domain.event.IDomainEvent;
import easy.domain.event.ISubscriber;
import easy.domain.event.TaskDomainEventManager;

public abstract class BaseApplication implements IApplication {

    private final IDomainEventManager manager;

    public BaseApplication() {
        this.manager = new TaskDomainEventManager();
    }

    public BaseApplication(IDomainEventManager manager) {
        this.manager = manager;
    }

    public void registerSubscriber(List<ISubscriber> item) {
        this.manager.registerSubscriber(item);
    }

    public void registerDomainEvent(List<Class<?>> events) {
        this.manager.registerDomainEvent(events);
    }



    protected <T> IBaseResult<T> write(T obj) {
        Result<T> result = new Result<>(obj);
        return result;
    }

    protected <T, EventDATA extends IDomainEvent> IBaseResult<T> writeAndPublishDomainEvent(
            T obj, EventDATA eventData) {
        try {
            this.publishEvent(eventData);
        } finally {
        }

        return this.write(obj);
    }

    protected <T extends IDomainEvent> void publishEvent(T obj) {
        this.manager.publishEvent(obj);
    }
}