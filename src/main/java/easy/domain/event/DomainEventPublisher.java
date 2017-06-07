package easy.domain.event;

import easy.domain.application.subscriber.IDomainEventSubscriber;
import easy.domain.application.subscriber.ISubscriber;

import java.util.List;
import java.util.concurrent.*;

/**
 * 事件发布器
 */
public class DomainEventPublisher {
    private static final ThreadPoolExecutor pool;

    static {

        pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    }

    /**
     * 发布事件
     *
     * @param aDomainEvent
     */
    @SuppressWarnings("unchecked")
    public <T extends IDomainEvent> void publish(final T aDomainEvent, List<ISubscriber> subscribers) {
        for (ISubscriber sub : subscribers) {
            IDomainEventSubscriber<T> subscribedTo = (IDomainEventSubscriber<T>) sub;

            if (subscribedTo != null && subscribedTo.subscribedToEventType() == aDomainEvent.getClass()) {
                Task<T> task = new Task<T>(subscribedTo, aDomainEvent);
                pool.execute(task);
                System.out.println("task count=" + pool.getTaskCount());
            }
        }
    }
}
